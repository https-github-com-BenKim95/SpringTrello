function enableEditMode() {
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