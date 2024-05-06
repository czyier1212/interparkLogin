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

            switch (data) {
                case "0":
                    console.log(`안녕하세요. [${userId}]님!`);
                    break;
                case "-1":
                    console.error("아이디 또는 비밀번호가 일치하지 않습니다. (로그인 오류 " + failCnt + "회)  5회 이상 로그인 오류 시 보안을 위해 로그인이 제한됩니다.");
                    break;
                case "-2":
                    console.error("Tiki회원이 아닙니다. 아이디 또는 비밀번호를 확인하시기 바랍니다.");
                    break;
                case "-3":
                    console.error("로그인 오류가 5회 초과했습니다.정보보호를 위해 자동입력방지 문자를 함께 입력해주세요.");
                    break;
                case "-4":
                    console.error("비밀번호 5회 입력 오류로 로그인이 제한되었습니다.");
                    break;
                case "-5":
                    console.error("비정상적인 접속 시도로 계정 보호를 위해 로그인 잠금 처리 되었습니다.");
                    break;
                case "-6":
                    console.error("아이디 또는 비밀번호가 일치하지 않습니다. SNS 회원이신 경우 연결된 SNS로 로그인해주세요");
                    break;
                case "-98":
                    console.error("아이디, 또는 비밀번호를 잘못 입력하셨습니다.");
                    break;
                case "1":
                    console.error("아이디를 입력해주세요.");
                    break;
                case "2":
                    console.error("비밀번호를 입력해주세요.");
                    break;
                case "3":
                    console.error("서비스 에러. 관리자게에 문의해주세요.");
                    break;
                case "4":
                    console.error("기획사 회원종류를 선택하세요.");
                    break;
                case "5":
                    console.error("부정거래 모니터링 시스템에 의해 로그인이 제한된 아이디 입니다.");
                    break;
                case "":
                    console.error("시스템에 이상이 있습니다. 잠시 후에 다시 이용해 주세요.");
                    break;
                default:
                    console.error("로그인에 실패하였습니다.");
                    break;
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
