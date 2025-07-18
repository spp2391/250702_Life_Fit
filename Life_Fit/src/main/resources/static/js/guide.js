function    openGuidePanel() {
    document.getElementById("guidePanel").classList.add("open");
}

function closeGuidePanel() {
    document.getElementById("guidePanel").classList.remove("open");
}
function openNoticePanel() {
    // 공지사항 패널 열기
    const panel = document.getElementById('noticePanel');
    if(panel) panel.classList.add('open');
}
