<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원가입</title>
<script type="text/javascript" src="/3rd/jquery/jquery-2.2.4.min.js"></script>
<!-- Bootstrap CSS -->
<link href="/3rd/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<style>
body {
	min-height: 100vh;
	background: -webkit-gradient(linear, left bottom, right top, from(#92b5db),
		to(#1d466c));
	background: -webkit-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
	background: -moz-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
	background: -o-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
	background: linear-gradient(to top right, #92b5db 0%, #1d466c 100%);
}

.input-form {
	max-width: 680px;
	margin-top: 30px;
	padding: 32px;
	background: #fff;
	-webkit-border-radius: 10px;
	-moz-border-radius: 10px;
	border-radius: 10px;
	-webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
	-moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
	box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15)
}

.form-control-ex {
    display: block;
    width: 100%;
    height: calc(2.25rem + 2px);
    padding: .375rem .75rem;
    font-size: 1rem;
    font-weight: 400;
    line-height: 1.5;
    color: #495057;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid #ced4da;
    border-radius: .25rem;
    transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
}

.form-control-ex.is-valid, .form-control-ex:valid {
    border-color: #28a745;
    padding-right: 2.25rem;
    background-repeat: no-repeat;
    background-position: center right calc(2.25rem / 4);
    background-size: calc(2.25rem / 2) calc(2.25rem / 2);
    background-image:url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 8 8'%3e%3cpath fill='%2328a745' d='M2.3 6.73L.6 4.53c-.4-1.04.46-1.4 1.1-.8l1.1 1.4 3.4-3.8c.6-.63 1.6-.27 1.2.7l-4 4.6c-.43.5-.8.4-1.1.1z'/%3e%3c/svg%3e");
}

.form-control-ex.is-invalid, .was-validated .form-control-ex:invalid {
    border-color: #dc3545;
    padding-right: 2.25rem;
    background-repeat: no-repeat;
    background-position: center right calc(2.25rem / 4);
    background-size: calc(2.25rem / 2) calc(2.25rem / 2);
    background-image:url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' fill='%23dc3545' viewBox='-2 -2 7 7'%3e%3cpath stroke='%23d9534f' d='M0 0l3 3m0-3L0 3'/%3e%3ccircle r='.5'/%3e%3ccircle cx='3' r='.5'/%3e%3ccircle cy='3' r='.5'/%3e%3ccircle cx='3' cy='3' r='.5'/%3e%3c/svg%3E");
}
</style>
</head>
<body>
	<div class="container">
		<div class="input-form-backgroud row">
			<div class="input-form col-md-12 mx-auto">
				<h4 class="mb-3">회원가입</h4>
				<form id="sign_frm" class="validation-form" novalidate>
					<div class="row">
						<div class="col-md-9 mb-3">
							<label for="email">ID(E-MAIL)</label> <input type="email"
								class="form-control-ex" id="email" name="email" placeholder="you@example.com" value="" autocomplete="off" required>
							<div id="email_msg" style="color:#dc3545;font-size: 80%;padding-top:3px"></div>
						</div>					
						<div class="col-md-3 mb-3">
							<label for=""></label>
							<button class="btn btn-info btn-lg btn-block" onclick="chkID()">중복체크</button>						
						</div>
					</div>

					<div class="mb-3">
						<label for=""name"">이름</label> <input type="text"
							class="form-control" id="name" name="name" autocomplete="off" placeholder=""
							required>
						<div class="invalid-feedback">이름을 입력해주세요.</div>
					</div>
					
					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="passwd">비밀번호</label> <input type="password"
								class="form-control" id="passwd" name="passwd" placeholder="" pattern="^(?=.*[0-9])(?=.*[a-z])\S{6,}" required />
							<div class="invalid-feedback">비밀번호를 입력해주세요.<p>(영문/숫자를 포함한 6글자 이상)</div>
						</div>					
						<div class="col-md-6 mb-3">
							<label for="passwd_cfm">비밀번호 확인</label> <input type="password"
								class="form-control-ex" id="passwd_cfm" placeholder="" pattern="^(?=.*[0-9])(?=.*[a-z])\S{6,}" required>
							<div id="passwd_cfm_msg" style="color:#dc3545;font-size: 80%;padding-top:3px"></div>
						</div>
					</div>	

					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="auth">일반사용자</label> <input type="radio" name="auth" value="ROLE_USER" checked="checked" required>
						</div>					
						<div class="col-md-6 mb-3">
							<label for="auth">관리자</label> <input type="radio" name="auth" value="ROLE_ADMIN,ROLE_USER" required> 
						</div>
					</div>
      
					<div class="mb-3"></div>
					<button class="btn btn-primary btn-lg btn-block">등록</button>
				</form>
			</div>
		</div>
		<footer class="my-3 text-center text-small">
			<p class="mb-1"></p>
		</footer>
	</div>
	<script>

	(function() {
	    var token  = "${_csrf.token}";
	    var header  = "${_csrf.headerName}";
		const userIdCheck = RegExp(/^[A-Za-z0-9_\-]{5,20}$/);
		
		var chk_email = "";
		var chk_dup = false;
		chkID = function(){
			event.preventDefault();

			let email = $("#email").val();
			console.log("email > "+email);
			if(email === ""){
				$("#email").addClass("form-control is-invalid");
				$("#email_msg").css("color","#dc3545").text("ID를 입력해주세요.");
				
				return;
			}
			
			$.ajax({
	            url: '/user/check/'+email,
	            type: 'post',
	            cache: false,
	        	beforeSend : function(xhr){
	        		xhr.setRequestHeader(header, token);
	        	    if(token && header) {
	        	        $(document).ajaxSend(function(event, xhr, options) {
	        	            xhr.setRequestHeader(header, token);
	        	        });
	        	    }	        		
	        	},	            
	            success:function(result){
	            	if(result){
	            		$("#emp_id").removeClass("form-control-ex is-valid").addClass("form-control-ex is-invalid");
	            		$("#emp_id_msg").css("color","#dc3545").text("사용할 수 없는 ID입니다.");
	            	}else{
	            		//중복체크한 ID저장
	            		chk_email = email;
	            		//중복체크 확인
	            		chk_dup = true;
	            		
	            		$("#email").removeClass("form-control-ex is-invalid").addClass("form-control-ex is-valid");
	            		$("#email_msg").css("color","#28a745").text("사용할 수 있는 ID입니다.");
	            	}
	            },
	            error:function(e){
	            	console.log('error >> '+e);
	            }
	        });


		}		
		
		window.addEventListener('load', () => { 
			const forms = document.getElementsByClassName('validation-form'); 
			Array.prototype.filter.call(forms, (form) => { 
				form.addEventListener('submit', function (event) {
					
					let id_chk_result = true;
					let pw_chk_result = true;
					let email = $("#email").val();
					
					if(email === ""){
						$("#email").addClass("form-control-ex is-invalid");
						$("#email_msg").css("color","#dc3545").text("ID를 입력해주세요.");
						id_chk_result = false;
					}else{
						//중복체크를 하지 않았거나 체크한ID와 입력값이 다를 경우
						console.log("chk_dup : "+chk_dup);
						console.log("email : "+email);
						console.log("chk_email : "+chk_email);
						if(!chk_dup || email != chk_email){
							$("#email").addClass("form-control-ex is-invalid");
							$("#email_msg").css("color","#dc3545").text("ID중복체크를 해주세요.");	
							id_chk_result = false;
						}
					}
					
					if($("#passwd_cfm").val() != $("#passwd").val()){
						$("#passwd_cfm").addClass("form-control-ex is-invalid");
						$("#passwd_cfm_msg").css("color","#dc3545").text("비밀번호를 확인해주세요.");	
						pw_chk_result = false;
					}else{
						$("#passwd_cfm").removeClass("form-control-ex is-invalid").addClass("form-control-ex is-valid");
						$("#passwd_cfm_msg").text("");	
						//pw_chk_result = true;
					}
					
					if (form.checkValidity() === false) { 
						
						form.classList.add('was-validated');
						
						/* 
						document.querySelectorAll("input").forEach(input => {
						  //input.addEventListener("invalid", () => {
							console.log("input.validity > "+input.id + "" + input.checkValidity());		
							form.classList.add("was-validated")
						    // 검증 후 폼 요소에 was-validated 클래스로 표시해 둔다
						    //document.forms[0].classList.add("was-validated")
						  //})
						})	
						 */
					}else{
						console.log("id_chk_result >> "+id_chk_result);
						console.log("pw_chk_result >> "+pw_chk_result);
						//ID유효성체크, 비밀번호 확인체크가 모두 완료된 경우
						if(id_chk_result && pw_chk_result){
							if(!confirm("가입 하시겠습니까?")){
							}else{
								var params = $("#sign_frm").serialize();
								console.log("params : "+params);
					            //var params = "email="+email+"&emp_name="+$('#emp_name').val()+"&emp_email="+$('#email').val()+"&emp_org_name="+$('#emp_org_name').val()+"&passwd="+$('#passwd').val();
					            $.ajax({
					                url:"/sign",
					                type:"POST",
					                data:params,
					                error:function(request,status,error){
					                    console.log(request.status);
					                },
						        	beforeSend : function(xhr){
						        		xhr.setRequestHeader(header, token);
						        	    if(token && header) {
						        	        $(document).ajaxSend(function(event, xhr, options) {
						        	            xhr.setRequestHeader(header, token);
						        	        });
						        	    }	        		
						        	},						                
					                success:function(data){
					                    console.log("data " + data);
					                    if(data){
					                    	alert("등록되었습니다.");
					                    	self.close();
					                    	//location.reload();
					                    }
					                }
					            });
							}
						}else{
						}
					}

					event.preventDefault(); 
					event.stopPropagation();	
					
				}, false); 
			}); 
		}, false);
	}());
	</script>
</body>
</html>

