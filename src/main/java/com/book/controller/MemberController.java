package com.book.controller;


import com.book.constant.Role;
import com.book.dto.MemberFormDto;
import com.book.entity.Member;
import com.book.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.http.HttpRequest;
import java.util.UUID;

@RequestMapping("/members")
@Controller
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from ;

    @GetMapping("/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping("/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {

        String user_id = memberFormDto.getUserid();
        int length = memberFormDto.getUserid().length();

        if(bindingResult.hasErrors()) {
            return "member/joinError";
        }
        try {
            if(user_id.substring(length-5,length).toString().equals("admin")) {
                memberFormDto.setRole(Role.ADMIN);
            } else {
                memberFormDto.setRole(Role.USER);
            }

            Member member = Member.createMember(memberFormDto, passwordEncoder);
            log.info(memberFormDto.getRole());
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        return "member/joinSuccess";
    }


    @GetMapping(value = "/login")
    public String loginMember() {
        return "member/memberLoginForm";
    }

    @GetMapping(value ="/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "member/memberLoginForm";
    }

    @GetMapping("/find")
    public String findIdandPasswd(@RequestParam("action") String action, Model model) {
        if(action.equals("id")){
            model.addAttribute("action", action);
            return "member/findAction";
        }
        else if(action.equals("passwd")){
            model.addAttribute("action", action);
            return "member/findAction";
        }
        return "member/memberLoginForm";
    }

    @PostMapping("/find")
    public String findAction(@RequestParam("action") String action, Model model, HttpServletRequest httpServletRequest) throws MessagingException {

        String name="", userid ="", email = "",pwd = "";
        if(action.equals("id")){
            name = httpServletRequest.getParameter("name");
        }
        else if(action.equals("passwd")){
            userid = httpServletRequest.getParameter("userid");
        }

        email = httpServletRequest.getParameter("email");

        if(action.equals("id")){
            Member member = memberService.findMember(name, email);
            model.addAttribute("member", member);
            return "member/findMemberResult";
        }
        else if(action.equals("passwd")){
            Member member = memberService.getPwdSearch(userid, email);
            if(member != null) {
                UUID uid = UUID.randomUUID();
                pwd = uid.toString().substring(0,6);
                memberService.setPwdChange(userid, passwordEncoder.encode(pwd));
            }

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("[book] 임시 비밀번호 안내");
            String content = "";
            content = content.replace("\n", "<br>");
            content += "<div style='text-align:center'>";
            content += "<h3><b><font color=skyblue>임시 비밀번호 생성</font></b></h3> <br>";
            content += "<span style='color:black'>안녕하세요 <b>" + member.getUserid()+"</b>님, </span><br>";
            content += "<span style='color:black'>귀하께서 요청하신 임시 비밀번호 </span><br>";
            content += "<span style='color:black'>수신을 위해 발송된 메일입니다. </span><br>";

            content += "<span style='color:black'><b>" + member.getUserid() + "</b>님의 임시 비밀번호는 <font color=skyblue>"+ pwd + "</font> 입니다. </span><br>";
            content += "<span style='color:black'>로그인 후에는 새로운 비밀번호로 변경하셔야 합니다.</span><br>";
            content += "<span style='color:black'>감사합니다. </span></font><br><br><br>";
            content += "<a href='http://localhost:8080/members/login' style='color:black'>로그인 하러가기 </a><br></div>";
            mimeMessageHelper.setText(content, true);
            javaMailSender.send(mimeMessage);

            model.addAttribute("action", action);
            return "member/findMemberResult";
        }
        return "/";
    }
}
