function login() {

    var param = {
        'userId': $("#loginId").val(),
        'userPassword': $("#loginPassword").val(),
    }

    $.ajax({
        url: '/user/login',
        type: 'POST',
        data: param,
    }).done(function () {
        alert("완료");
        window.location.replace("/");
    }).fail(function () {
            alert("실패");
            // history.back();
        }
    );
}

function logout() {

    $.ajax({
        url: '/user/logout',
        type: 'GET',
    }).done(function () {
        alert("완료");
        window.location.replace("/");
    }).fail(function () {
            alert("실패");
            // history.back();
        }
    );
}