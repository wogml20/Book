let price = document.querySelectorAll(".price")[0].innerHTML
price *= 1
price.toLocaleString()
document.querySelectorAll(".price")[0].innerHTML = price.toLocaleString()