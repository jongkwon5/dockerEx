function toggleAll(source) {
    const checkboxes = document.querySelectorAll(".user-checkbox");
    checkboxes.forEach(cb => cb.checked = source.checked);
}

function deleteSelectedUsers() {
    const selected = [];

    document.querySelectorAll('.user-checkbox:checked').forEach(cb => {
        const row = cb.closest('tr');
        const id = row.querySelector('.user-id').value;
        const name = row.querySelector('.user-name').value;

        selected.push({ id: parseInt(id), name });
    });

    if (selected.length === 0) {
        alert("삭제할 사용자를 선택하세요.");
        return;
    }

    if (!confirm("정말 삭제하시겠습니까?")) return;

    fetch('/users', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(selected)
    })
        .then(res => {
            if (res.ok) {
                alert("삭제 완료되었습니다.");
                location.reload();
            } else {
                alert("삭제 실패");
            }
        });

    return false;

}