$(document).ready(function () {
  getBoards();

  // 버튼 요소 가져오기
  const button = document.getElementById('member');

  // 버튼 클릭 이벤트 리스너 등록
  button.addEventListener('click', () => {
    console.log("test");
    // window.location.href = 'http://localhost:8080/invite';
  });
});

function getBoards() {
  fetch('/api/board', {
    method: 'GET',
    headers: {'Content-Type': 'application/json'}
  })
      .then(response => response.json())
      .then(data => {
        $('#board-list').empty();

        data.forEach(board => {
          let title = board.title;
          let color = board.color;
          let description = board.description;
          let userList = board.userList;

          let boardId = board.board_id;
          let columnListId = board.column_list_id;

          // Create a unique ID for the card
          let cardId = `card-${boardId}-${columnListId}`;

          let temp_html = `
                           <div class="col mb-5">
                            <a href="/api/board/${boardId}">
                              <div class="card feed border-0 h-100 text-black" id="${cardId}">
                                <div class="card-body bg-light bg-gradient rounded h-100">
                                  <p class="card-title fw-bold fs-5">보드명: ${title}</p>
                                  <p class="card-text none">${color}</p>
                                  <p class="card-text mt-2 fs-6">보드설명: ${description}</p>
                                  <p class="card-text fs-6">참여자: ${userList}</p>
                                </div>
                              </div>
                            </a>
                            <div class="d-grid gap-2 d-md-flex justify-content-md-center my-3">
                              <button th:if="${boardId} != null" class="btn btn-light btn-sm" id="member" type="button" onclick="inviteMember()">Member</button>
                              <button th:if="${boardId} != null" class="btn btn-light btn-sm" id="modify-btn" type="button" onclick="enableEdit()">Edit</button>
                              <button th:if="${boardId} != null" class="btn btn-light btn-sm none" id="confirm-btn" type="button" onclick="saveChanges()">Confirm</button>
                               <button class="btn btn-outline-light btn-sm delete-board-btn" data-boardid="${boardId}" type="button">Delete</button>
                            </div>
                          </div>
                    `;

          $('#board-list').append(temp_html);

          const hexColor = color;
          const card = document.getElementById(cardId);
          card.style.backgroundColor = hexColor;
        });
      });
}
// 드래그앤드롭
$(document).ready(function () {
  $("#board-list").sortable({
    axis: "x",
    handle: ".card",
    update: function (event, ui) {
      const cardOrder = $("#board-list").sortable("toArray");

      console.log("Updated card order:", cardOrder);
    }
  });
  $("#board-list").disableSelection();
});


// 게시글 삭제
// Delete 버튼 클릭 이벤트 리스너 등록
$(document).on('click', '.delete-board-btn', function () {
  const boardId = $(this).data('boardid');

  if (confirm("정말로 보드를 삭제하시겠습니까?")) {
    deleteBoard(boardId);
  }
});

function deleteBoard(boardId) {
    fetch(`/api/board/${boardId}`, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                return response.json().then(data => {
                    throw new Error(data.message);
                });
            }
        })
        .then(data => {
            console.log("보드 삭제 완료");
            // 보드 삭제 후 보드 목록을 다시 불러옴
            getBoards();
        })
        .catch(error => {
            console.error("보드 삭제 중 에러 발생:", error);
        });
}


