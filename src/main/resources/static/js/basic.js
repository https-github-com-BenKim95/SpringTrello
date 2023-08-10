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
                        <a href="/board/${boardId}/column/column_list/${columnListId}" class="col">
                            <div class="card feed border-0 h-100 text-black" id="${cardId}">
                                <div class="card-body bg-light bg-gradient rounded">
                                    <h5 class="card-title fw-bold">${title}</h5>
                                    <p class="card-text none">${color}</p>
                                    <p class="card-text fw-bold">${description}</p>
                                    <p class="card-text fw-bold">${userList}</p>
                                </div>
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