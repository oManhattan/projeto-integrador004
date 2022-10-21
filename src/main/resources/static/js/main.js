let acao1 = function() {
    console.log("Botão pressionado")
}

let acao2 = function() {
    alert("Botão está funcionando")
}

document.querySelector("#botao").addEventListener("click", acao1)
document.querySelector("#botao2").addEventListener("click", acao2)