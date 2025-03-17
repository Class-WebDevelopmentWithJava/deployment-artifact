// 삭제 기능
// 삭제 버튼 가져오기
const deleteButton = document.getElementById('delete-btn');

if(deleteButton) { // false, 0, "", undefined거나 null인 경우 false로 간주
    deleteButton.addEventListener('click', event => {

        let id = document.getElementById('article-id').value;

        fetch(`/api/articles/${id}`, {
            method: 'DELETE'
        })
        .then((response) => {
            console.log(response);

            if(response.status === 200) { // 자바스크립트 === 타입까지 비교
                alert('삭제가 완료되었습니다.');

                location.replace('/articles');
            }
        });
    });
}

// 수정 기능
// id가 modify-btn인 엘리먼트 조회
const modifyButton = document.getElementById('modify-btn');

if(modifyButton) {
    // 클릭 이벤트가 감지되면 수정 API 요청
    modifyButton.addEventListener('click', event => {
        // URL에?id=값으로 세팅되어있다는 전제
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/api/articles/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
        .then((response) => {
            if(response.status === 200) {
                alert('수정이 완료되었습니다.');
                location.replace(`/articles/${id}`);
            }
        });
    });
}

// 등록 기능
// id가 create-btn인 엘리먼트 조회
const createButton = document.getElementById('create-btn');

if(createButton) {
    createButton.addEventListener("click", (event) => {
        fetch("/api/articles", { // /api/articles, POST, Body (title ,content)
            method: "POST",
            headers: {
                "Content-type" : "application/json"
            },
            // stringify : 자바스크립트의 값을 JSON 문자열로 변환
            body: JSON.stringify({
                title : document.getElementById("title").value,
                content : document.getElementById("content").value,
                author : {
                    id : 1
                }
            })
        }).then((response) => {
            if(response.status === 201) {
                alert("등록 완료되었습니다.");
                location.replace("/articles");
            }
        });
    });
}