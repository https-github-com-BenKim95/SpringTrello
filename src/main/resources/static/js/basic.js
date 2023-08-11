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
        $('#board-list').empty()
        for (let i = data.length - 1; i >= 0; i--) {
          console.log([data[i]])
          let title = data[i].title;
          let color = data[i].color;
          let description = data[i].description;
          let modifiedAt = data[i].modifiedAt;

          let temp_html = `<a href=/api/board class="col">
                                        <div class="card feed border-0">
                                            <div class="card-body">
                                                <h5 class="card-title fw-bold">${title}</h5>
                                                <p class="card-text text-truncate">${color}</p>
                                                <p class="card-text fw-bold">${description}</p>
                                                <p class="card-text text-muted">${modifiedAt}</p>
                                            </div>
                                        </div>
                                      </a>`
          $('#board-list').append(temp_html)
        }
      })
}