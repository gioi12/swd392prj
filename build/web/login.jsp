<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
    <head>
        <!-- Basic metadata -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <link rel="icon" href="images/logo/logo_icon.png" type="image/x-icon">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="css/bootstrap.min.css">

        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f9;
                padding: 20px;
            }

            .container {
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                height: 100vh;
            }

            .login_form {
                width: 100%;
                max-width: 400px;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 8px;
                background-color: #ffffff;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .field {
                margin-bottom: 15px;
            }

            .field label {
                font-weight: bold;
                margin-bottom: 5px;
            }

            .field input {
                width: 100%;
                padding: 10px;
                font-size: 16px;
                border: 2px solid #ccc;
                border-radius: 5px;
                outline: none;
                transition: border-color 0.3s ease;
            }

            .field input:focus {
                border-color: #007BFF;
                box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
            }

            .main_bt {
                width: 100%;
                padding: 12px;
                background-color: #007BFF;
                color: white;
                border: none;
                border-radius: 5px;
                font-size: 16px;
                cursor: pointer;
                margin-top: 10px;
            }

            .main_bt:hover {
                background-color: #0056b3;
            }

            .forgot {
                text-decoration: none;
                color: #007BFF;
                font-size: 14px;
            }

            .forgot:hover {
                text-decoration: underline;
            }

            .field .form-check-label {
                font-size: 14px;
            }

            .field .form-check-input {
                margin-top: 2px;
            }

            .btn-google {
                width: 100%;
                padding: 12px;
                background-color: #db4437;
                color: white;
                border: none;
                border-radius: 5px;
                font-size: 16px;
                cursor: pointer;
                margin-top: 10px;
            }

            .btn-google:hover {
                background-color: #c1351d;
            }
        </style>
        
    </head>
    <body>
        <div class="container">
            <div class="login_form">
                <h2 class="text-center">Đăng nhập</h2><br/>
                <form id="loginForm" action="LoginController" method="POST" autocomplete="off">
                    <fieldset>
                        <div class="field">
                            <label class="label_field">Tài khoản</label>
                            <input type="text" name="username" value="${cookie.username.value}" placeholder="Nhập tài khoản" required="" />
                        </div>
                        <div class="field">
                            <label class="label_field">Mật khẩu</label>
                            <input type="password" name="password" value="${cookie.password.value}" placeholder="Nhập mật khẩu" required=""/>
                            <div style="color: red; margin-top: 5px;">
                                <c:if test="${not empty wrongAcc}">
                                    <script>
                                        alert("${wrongAcc}");
                                    </script>
                                </c:if>
                            </div>
                        </div>

                        <div class="field">
                            
                            <a class="forgot" href="register.jsp">Đăng kí tài khoản</a>
                            <a class="forgot" href="requestchangepass.jsp">Quên mật khẩu?</a> 
                        </div>
                        <div class="field margin_0">
                            <button class="main_bt" type="submit">Đăng nhập</button>
                            <button class="btn-google" type="button" onclick="loginWithGoogle()">Đăng nhập Google</button>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>

        <!-- jQuery -->
        <script src="js/jquery.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>

        <script>
            function loginWithGoogle() {
                window.location.href = 'https://accounts.google.com/o/oauth2/auth?scope=profile%20email&redirect_uri=http://localhost:8080/LoginSystem/loginwithgooglehandler&response_type=code&client_id=1017934178422-1is43ql9uc2gldh36celc6o8mt2bolaj.apps.googleusercontent.com';
            }

            function getUrlParameter(name) {
                name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
                var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
                var results = regex.exec(location.search);
                return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
            }

            // Hàm để cập nhật giá trị pid vào input ẩn
            function updatePidInForm() {
                var pid = getUrlParameter('pid');
                document.getElementById('pid').value = pid;
            }

            // Gọi hàm cập nhật khi trang được load
            window.onload = function () {
                updatePidInForm();
            };

            document.getElementById('loginForm').addEventListener('submit', function () {
                updatePidInForm(); // Cập nhật giá trị pid trước khi submit
            });
        </script>

    </body>
</html>
