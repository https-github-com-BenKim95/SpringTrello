const boardListElement = document.getElementById('board-list');

// 가정: 백엔드에서 받은 데이터
const boards = [

];

// 보드 목록을 생성하여 화면에 표시
function renderBoardList() {
    boardListElement.innerHTML = '';
    boards.forEach(board => {
        const boardElement = document.createElement('li');
        boardElement.className = 'board';
        boardElement.innerHTML = `
            <h2>${board.title}</h2>
            <p>${board.description}</p>

        `;
        boardListElement.appendChild(boardElement);
    });
}

// 보드 목록을 렌더링
renderBoardList();
