const btnSingUp = document.getElementById('singUp')
btnSingUp.addEventListener('click', onPress);

function onPress(){
    location.href = './conta?acao=ExibirCadastroConta'
}