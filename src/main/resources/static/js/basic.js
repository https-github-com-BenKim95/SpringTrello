const draggables = document.querySelectorAll(".draggable");
const containers = document.querySelectorAll(".container");

draggables.forEach(draggable => {
  draggable.addEventListener("dragstart", () => {
    draggable.classList.add("dragging");
  });

  draggable.addEventListener("dragend", () => {
    draggable.classList.remove("dragging");
  });
});

containers.forEach(container => {
  container.addEventListener("dragover", e => {
    e.preventDefault();
    const afterElement = getDragAfterElement(container, e.clientX);
    const draggable = document.querySelector(".dragging");
    if (afterElement === undefined) {
      container.appendChild(draggable);
    } else {
      container.insertBefore(draggable, afterElement);
    }
  });
});

function getDragAfterElement(container, x) {
  const draggableElements = [
    ...container.querySelectorAll(".draggable:not(.dragging)"),
  ];

  return draggableElements.reduce(
      (closest, child) => {
        const box = child.getBoundingClientRect();
        const offset = x - box.left - box.width / 2;
        // console.log(offset);
        if (offset < 0 && offset > closest.offset) {
          return { offset: offset, element: child };
        } else {
          return closest;
        }
      },
      { offset: Number.NEGATIVE_INFINITY },
  ).element;
}

idx.init();

$(document).ready(function () {
  getPost();
});
function getPost() {
  fetch('/api/', {
    method: 'GET',
    headers: {'Content-Type': 'application/json'}
  })
      .then(response => response.json())
      .then(data => {
        $('#post-list').empty()
        for (let i = data.length - 1; i >= 0; i--) {
          console.log([data[i]])
          let title = data[i].title;
          let contents = data[i].contents;
          let userName = data[i].username;
          let modifiedAt = data[i].modifiedAt;

          let image;
          if(data[i].image === '') {
            image = "/images/img.png"
          } else {
            image = data[i].image;
          }

          let postId = data[i].id

          let temp_html = `<a href = post/detail/${postId} class="col">
                                        <div class="card feed border-0">
                                            <img src="${image}" class="card-img-top img-fluid" alt="sampleImage" style="height: 14rem; overflow-clip: margin;">
                                            <div class="card-body">
                                                <h5 class="card-title fw-bold">${title}</h5>
                                                <p class="card-text text-truncate">${contents}</p>
                                                <p class="card-text fw-bold">${userName}</p>
                                                <p class="card-text text-muted">${modifiedAt}</p>
                                            </div>
                                        </div>
                                      </a>`
          $('#post-list').append(temp_html)
        }
      })
}