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

// $(document).ready(function () {
//   getBoards();
// });
// function getBoards() {
//   fetch('/api/board', {
//     method: 'GET',
//     headers: {'Content-Type': 'application/json'}
//   })
//       .then(response => response.json())
//       .then(data => {
//         $('#board-list').empty()
//         for (let i = data.length - 1; i >= 0; i--) {
//           console.log([data[i]])
//           let title = data[i].title;
//           // let userList = data[i].userList;
//           let color = data[i].color;
//           let description = data[i].description;
//           let createdAt = data[i].createdAt;
//           let modifiedAt = data[i].modifiedAt;
//
//           // let image;
//           // if(data[i].image === '') {
//           //   image = "/images/img.png"
//           // } else {
//           //   image = data[i].image;
//           // }
//
//           let postId = data[i].id
//
//           let temp_html = `<a href = api/board class="col">
//                                     <div class="card feed border-0">
//                                       <div class="card-body">
//                                         <h5 class="card-title fw-bold">${title}</h5>
// <!--                                        <p class="card-text fw-bold">${userList}</p>-->
//                                         <p class="card-text fw-bold">${color}</p>
//                                         <p class="card-text fw-bold">${description}</p>
//                                         <p class="card-text text-muted">${createdAt}</p>
//                                         <p class="card-text text-muted">${modifiedAt}</p>
//                                       </div>
//                                     </div>
//                                   </a>`
//           $('#board-list').append(temp_html)
//         }
//       })
// }

function Create() {
  let title = $('#title').val();
  let color = $('#color').val();
  let description = $('#description').val();

  console.log(title, color, description);

  $.ajax({
    type: "POST",
    url: "/api/board",
    contentType: "application/json",
    data: JSON.stringify({title: title, color: color, description: description}),
    success: function(response, status, xhr) {
      // 요청이 성공한 경우 처리할 로직을 작성합니다.
      console.log("POST 요청이 성공했습니다.");

      // 서버 응답이 성공적으로 왔을 때 처리
      if (xhr.status === 200) {
        Swal.fire({
          icon: 'success',
          title: '보드 생성 완료!'
        }).then((result) => {
          if (result.isConfirmed) {
            window.location.href = "http://localhost:8080";
          }
        });
      } else {
        Swal.fire({
          icon: 'error',
          title: '보드 생성 실패'
        }).then((result) => {
          if (result.isConfirmed) {
            window.location.href = "http://localhost:8080";
          }
        });
      }
    },
    error: function(xhr, status, error) {
      // 요청이 실패한 경우 처리할 로직을 작성합니다.
      console.log("POST 요청이 실패했습니다.");
      console.log(xhr.responseText);
      Swal.fire({
        icon: 'error',
        title: '보드 작성 실패'
      }).then((result) => {
        if (result.isConfirmed) {
          window.location.href = "http://localhost:8080";
        }
      });
    }
  });
}
// 게시글 삭제
function Delete() {
  let url = window.location.href;
  let id = url.replace("http://localhost:8080/api/board/", "");
  console.log(id);

  $.ajax({
    type: "DELETE",
    url: `/api/board/${id}`,
    contentType: "application/json",
    success: function(response, status, xhr) {
      if (xhr.status === 200) {
        // 요청이 성공한 경우 처리할 로직을 작성합니다.
        console.log("DELETE 요청이 성공했습니다.");
        Swal.fire({
          icon: 'success',
          title: '보드 삭제 완료!'
        }).then((result) => {
          if (result.isConfirmed) {
            window.location.href = "http://localhost:8080";
          }
        });
      } else {
        Swal.fire({
          icon: 'error',
          title: '작성자만 삭제 가능합니다'
        }).then((result) => {
          if (result.isConfirmed) {
            window.location.href = `http://localhost:8080/api/post/${id}`;
          }
        });
      }
    },
    error: function(xhr, status, error) {
      // 요청이 실패한 경우 처리할 로직을 작성합니다.
      console.log("DELETE 요청이 실패했습니다.");
      console.log(xhr.responseText);
      Swal.fire({
        icon: 'error',
        title: '작성자만 삭제 가능합니다'
      }).then((result) => {
        if (result.isConfirmed) {
          window.location.href = `http://localhost:8080/api/post/${id}`;
        }
      });
    }
  });
}

function Edite() {
  let params = new URLSearchParams(location.search);
  let id = params.get('id');

  let title = $('#title').val();
  let color = $('#color').val();
  let description = $('#description').val();


  $.ajax({
    type: "PUT",
    url: `/api/board/${id}`,
    contentType: "application/json",
    data: JSON.stringify({title: title, color: color, description: description}),
    success: function(response, status, xhr) {
      // 요청이 성공한 경우 처리할 로직을 작성합니다.
      console.log("PUT 요청이 성공했습니다.");

      // 서버 응답이 성공적으로 왔을 때 처리
      if (xhr.status === 200) {
        Swal.fire({
          icon: 'success',                         // Alert 타입
          title: '보드 수정 완료!'        // Alert 제목
        }).then((result) => {
          if (result.isConfirmed) {
            window.location.href = `http://localhost:8080/api/board/${id}`;
          }
        });
      } else {
        Swal.fire({
          icon: 'error',
          title: '보드 수정 실패'
        }).then((result) => {
          if (result.isConfirmed) {
            window.location.href = `http://localhost:8080/api/board/${id}`;
          }
        });
      }
    },
    error: function(xhr, status, error) {
      // 요청이 실패한 경우 처리할 로직을 작성합니다.
      console.log("POST 요청이 실패했습니다.");
      console.log(xhr.responseText);
      Swal.fire({
        icon: 'error',
        title: '작성자만 수정 가능합니다'
      }).then((result) => {
        if (result.isConfirmed) {
          window.location.href = `http://localhost:8080/api/board/${id}`;
        }
      });
    }
  });
}
