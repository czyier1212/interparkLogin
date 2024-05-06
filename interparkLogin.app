const https = require('https'); 
const readline = require('readline'); 
const querystring = require('querystring'); 

function interpark_login(userId, userPwd) {
    const postData = querystring.stringify({
        "frUID": userId, 
        "frPWD": userPwd 
    });

    // 요청 옵션
    const options = {
        hostname: 'ticket.interpark.com', 
        port: 443, 
        path: '/gate/TPLoginCheck_Return.asp', 
        method: 'POST', 
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded', 
            'Content-Length': Buffer.byteLength(postData) 
        }
    };

    // 1. HTTP 요청 
    const req = https.request(options, (res) => {
        let data = ''; 

        // 3. 응답 수신
        res.on('data', (chunk) => {
            data += chunk; 
        });

        // 응답 처리
        res.on('end', () => {
		console.log('커밋테스트 111');
            console.log('Response:', data); 

            if (data === '0') {
                console.log(`안녕하세요. [${userId}]님!`); 
            } else {
                console.error('로그인에 실패하였습니다.ㅠㅠ'); 
            }
        });
    });

    req.on('error', (e) => {
        console.error('서버 요청 실패(login error)', e); 
    });

    // 2. 요청 데이터 전송
    req.write(postData); 
    req.end(); 
}


const rl = readline.createInterface({
    input: process.stdin, 
    output: process.stdout 
});

rl.question('id: ', (userId) => {
    rl.question('pwd: ', (userPwd) => {
        interpark_login(userId, userPwd);
        rl.close(); 
    });
});
