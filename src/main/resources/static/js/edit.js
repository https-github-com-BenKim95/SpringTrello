function enableEdit() {
  // Edit 버튼을 누르면 인풋 박스를 수정 가능한 상태로 변경
  document.getElementById('title').removeAttribute('readonly');
  document.getElementById('color').removeAttribute('readonly');
  document.getElementById('description').removeAttribute('readonly');

  // Edit 버튼을 감추고, Save 버튼을 보이게 변경
  document.getElementById('modify-btn').style.display = 'none';
  document.getElementById('save-btn').style.display = 'inline-block';
}

function saveChanges() {
  // 변경된 내용을 가져와서 서버로 전송하는 로직 추가
  var title = document.getElementById('title').value;
  var color = document.getElementById('color').value;
  var description = document.getElementById('description').value;

  // 서버로 변경된 내용 전송하는 코드 추가

  // 인풋 박스를 읽기 전용 상태로 변경
  document.getElementById('title').setAttribute('readonly', true);
  document.getElementById('color').setAttribute('readonly', true);
  document.getElementById('description').setAttribute('readonly', true);

  // Save 버튼을 감추고, Edit 버튼을 보이게 변경
  document.getElementById('modify-btn').style.display = 'inline-block';
  document.getElementById('save-btn').style.display = 'none';
}



// 시게글 정수
function Edit() {
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
            window.location.href = "http://localhost:8080/memberMain";
          }
        });
      } else {
        Swal.fire({
          icon: 'error',
          title: '보드 수정 실패'
        }).then((result) => {
          if (result.isConfirmed) {
            window.location.href = `http://localhost:8080/memberMain`;
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
        title: '생성자만 수정 가능합니다'
      }).then((result) => {
        if (result.isConfirmed) {
          window.location.href = `http://localhost:8080/memberMain`;
        }
      });
    }
  });
}
