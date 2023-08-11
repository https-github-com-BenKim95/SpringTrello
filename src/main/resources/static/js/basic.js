$(document).ready(function () {
  getBoards();
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
                        <a class="col mb-5" href="/board/${boardId}/column/column_list/${columnListId}">
                          <div class="card feed border-0 h-100 text-black" id="${cardId}">
                            <div class="card-body bg-light bg-gradient rounded h-100">
                              <p class="card-title fw-bold fs-5">보드명: ${title}</p>
                              <p class="card-text none">${color}</p>
                              <p class="card-text mt-2 fs-6">보드설명: ${description}</p>
                              <p class="card-text fs-6">참여자: ${userList}</p>
                            </div>
                          </div>
                          <div class="d-grid gap-2 d-md-flex justify-content-md-center my-3">
                            <button th:if="${board.board_id} != null" class="btn btn-light btn-sm" type="button" onclick="Edit()">Edit</button>
                            <button th:if="${board.board_id} != null" class="btn btn-outline-light btn-sm" type="button" onclick="Delete()">Delete</button>
                          </div>
                        </a>
                        
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