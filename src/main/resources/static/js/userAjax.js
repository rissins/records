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
        }
    );
}

function signup() {

    var param = {
        'userId': $("#signupId").val(),
        'userPassword': $("#signupPassword").val(),
    }

    $.ajax({
        url: '/user/signup',
        type: 'POST',
        data: param,
    }).done(function () {
        alert("회원가입 완료");
        window.location.replace("/");
    }).fail(function () {
            alert("회원가입 실패");
        }
    );
}

function overlapCheck() {

    var userId = $('#signupId').val();

    $.ajax({
        url: '/user/check',
        type: 'POST',
        data: {'userId': userId },
    }).done(function (data) {
        // console.log(data);
        $('#overlapCheckDiv').text(data);
    }).fail(function () {
        }
    );
}

function deleteUser() {

    $.ajax({
        url: '/user/3',
        type: 'DELETE',
        data: {'userId': userId },
    }).done(function (data) {
        alert("삭제성공")
    }).fail(function () {
        alert("삭제실패")
        }
    );
}