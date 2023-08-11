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

  fetch('/api/myboards', {
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

          function gotoboard() {
            window.location.href = `http://localhost:8080/board/${boardId}`;
          }

          // Create a unique ID for the card
          let cardId = `card-${boardId}-${columnListId}`;

          // href="/board/${boardId}/column/column_list/${columnListId}"
          let temp_html = `
                        <a class="col mb-5"> 
                          <div class="card feed border-0 h-100 text-black" id="${cardId}" data-board-id="${boardId}">
                            <div class="card-body bg-light bg-gradient rounded h-100">
                              <p class="card-title fw-bold fs-5">보드명: ${title}</p>
                              <p class="card-text none">${color}</p>
                              <p class="card-text mt-2 fs-6">보드설명: ${description}</p>
                              <p class="card-text fs-6">참여자: ${userList}</p>
                            </div>
                          </div>
                          <div class="d-grid gap-2 d-md-flex justify-content-md-center my-3">
                            <button id="member" th:if="${boardId} != null" class="btn btn-light btn-sm" type="button">Member</button>
                            <button th:if="${boardId} != null" class="btn btn-light btn-sm" type="button">Edit</button>
                            <button th:if="${boardId} != null" class="btn btn-outline-light btn-sm" type="button" onclick="Delete()">Delete</button>
                          </div>
                        </a>
                    `;



          $('#board-list').append(temp_html);
          const hexColor = color;
          const card = document.getElementById(cardId);
          card.style.backgroundColor = hexColor;
        });

        // 버튼 요소 가져오기
        const memberButtons = document.querySelectorAll('.member-button');

        // 버튼 클릭 이벤트 리스너 등록
        memberButtons.forEach(button => {
          button.addEventListener('click', function() {
            const boardId = this.getAttribute('data-board-id');
            window.location.href = `http://localhost:8080/board/${boardId}`;
          });
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

