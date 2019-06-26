<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ja" lang="ja">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="content-language" content="ja" />
<meta http-equiv="content-script-type" content="text/javascript" />
<meta http-equiv="content-style-type" content="text/css" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta name="robots" content="noindex" />

<title>API TEST</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<script>
    var clientId = null;
    var clientSecret = null;
    var accessToken = null;
    var refreshToken = null;
    function publishClientInfo() {
		$.ajax({
			contentType : 'application/json;charset=UTF-8;application/x-www-form-urlencoded',
			type : 'GET',
			url : 'http://localhost:8080/springMVC/url/v1/api/publishClientInfo',
			async : false,
			cache : false,
			dataType : 'json',
			success : function(result) {
				console.log(result);
				clientId = result.client_id;
				clientSecret = result.client_secret;
			},
			error : function() {
				console.log('ERROR');
			}
		});
	}

	function token() {
		if(clientId == null || clientSecret == null) {
			alert('plz get ur client info.');
		} else {
			$.ajax({
				contentType : 'application/json;charset=UTF-8;application/x-www-form-urlencoded',
				type : 'POST',
				url : 'http://localhost:8080/springMVC/url/v1/oauth/token?client_id=' + clientId + '&client_secret=' + clientSecret + '&grant_type=password&scope=trust&username=user001&password=123456',
				async : false,
				cache : false,
				dataType : 'json',
				success : function(result) {
					console.log(result);
					accessToken = result.access_token;
					refreshToken = result.refresh_token;
				},
				error : function() {
					console.log('ERROR');
				}
			});
		}

	}

	function tokenByRefreshToken() {
		if(refreshToken == null) {
			alert('plz get ur refresh token.');
		} else {
			$.ajax({
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Authorization", 'Bearer ' + accessToken);
				},
				contentType : 'application/json;charset=UTF-8;application/x-www-form-urlencoded',
				type : 'POST',
				url : 'http://localhost:8080/springMVC/url/v1/oauth/token?refresh_token=' + refreshToken + '&client_id=' + clientId + '&client_secret=' + clientSecret + '&grant_type=refresh_token&scope=trust',
				async : false,
				cache : false,
				dataType : 'json',
				success : function(result) {
					console.log(result);
					accessToken = result.access_token;
					refreshToken = result.refresh_token;
				},
				error : function() {
					console.log('ERROR');
				}
			});
		}
	}

	function getPage() {
		$.ajax({
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Authorization", 'Bearer ' + accessToken);
			},
			contentType : 'application/json;charset=UTF-8;application/x-www-form-urlencoded',
			type : 'GET',
			url : 'http://localhost:8080/springMVC/url/v1/api/member/1',
			async : false,
			cache : false,
			dataType : 'json',
			success : function(result) {
				console.log(result);
			},
			error : function() {
				console.log('ERROR');
			}
		});
	}

	function getNoAuthPage() {
		$.ajax({
			contentType : 'application/json;charset=UTF-8;application/x-www-form-urlencoded',
			type : 'GET',
			url : 'http://localhost:8080/springMVC/url/v1/api/noauth/1',
			async : false,
			cache : false,
			dataType : 'json',
			success : function(result) {
				console.log(result);
			},
			error : function() {
				console.log('ERROR');
			}
		});
	}

	function clearOauthRecords() {
		$.ajax({
			contentType : 'application/json;charset=UTF-8;application/x-www-form-urlencoded',
			type : 'GET',
			url : 'http://localhost:8080/springMVC/url/v1/api/clear_oauth',
			async : false,
			cache : false,
			dataType : 'json',
			success : function(result) {
				console.log(result);
			},
			error : function() {
				console.log('ERROR');
			}
		});
	}
</script>
</head>
<body>
	<noscript>
		<p>JavaScriptが無効なため一部の機能が動作しません。動作させるためにはJavaScriptを有効にしてください。またはブラウザの機能をご利用ください。</p>
	</noscript>
	<div id="main">
		<a href="javascript:void(0);" onclick="publishClientInfo();">Publish ClientInfo</a><br />
		<a href="javascript:void(0);" onclick="token();">Get Token</a><br />
		<a href="javascript:void(0);" onclick="tokenByRefreshToken();">Get Token by refresh token</a><br />
		<a href="javascript:void(0);" onclick="getPage();">Get UserInfo</a><br />
		<a href="javascript:void(0);" onclick="getNoAuthPage();">Get No Auth UserInfo</a><br />
		<a href="javascript:void(0);" onclick="clearOauthRecords();">Clear expired records</a><br />
	</div>
</body>
</html>
