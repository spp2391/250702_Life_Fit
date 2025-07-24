var markers = [];
var bounds = new kakao.maps.LatLngBounds();
var infowindow = new kakao.maps.InfoWindow({zIndex:10,disableAutoPan:true});

function showModal(id) {
    let inputData = data.find((item)=> item.id === id);
    let modalWindow = `<div class="modal-content">
                        <span id="closeModal" class="close" onclick="closeModal()">&times;</span>
                        <h3>자세히 보기</h3>
                        <p>${inputData.title}</p>
                        <p>${inputData.description}</p>
                        <p>${inputData.address}</p>
                        <p><a href="${inputData.url}">카카오 맵 링크</a></p>
                    </div>`
    modal.innerHTML = modalWindow;
    modal.style.display="block";
    panTo(inputData.lat, inputData.lng);
}
function closeModal(){
    modal.style.display="none";
}
// 마커 설정. (다수)
function setMarkers(result) {
    resetBounds();
    for (const data of result) {
        // 각 검색 결과에 대해 좌표와 장소명을 불러온다.
        let position = new kakao.maps.LatLng(data.lat, data.lng);
        let title = data.title;
        // LatLngBounds의 bounds를 검색결과에 따라 확장.
        bounds.extend(position);
        // 마커 세팅.
        var marker = new kakao.maps.Marker({
            map: map,
            position: position,
            title: title,
            zIndex: 1
        });
        (function (marker, title, result) {
            setMarkerHover(marker, title);
            // 마커를 클릭할 시 상세보기 페이지가 보이도록 설정.
            if (result.address) {
                setMarkerClick(marker, result)
            }
        })(marker, title, data);
        // 마커 리스트에 마커 추가.
        markers.push(marker);
    }
    // TODO: 지도를 검색결과가 보이는 곳으로 이동.
    map.setBounds(bounds);
}
// 모든 마커 삭제.
function removeMarker() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
}
// 바운더리 리셋.
function resetBounds() {
    bounds = new kakao.maps.LatLngBounds();
}
//
function panTo(lat, lng) {
    // 이동할 위도 경도 위치를 생성합니다
    console.log(lat, lng);
    var moveLatLon = new kakao.maps.LatLng(lat, lng);
    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.panTo(moveLatLon);
}
// 마커의 호버 이벤트 설정
function setMarkerHover(marker, title) {
    kakao.maps.event.addListener(marker, 'mouseover', function() {
        displayInfowindow(marker, title);
    });
    kakao.maps.event.addListener(marker, 'mouseout', function() {
        infowindow.close();
    });
}
// 마커의 클릭 이벤트 설정 (상세보기 팝업)
function setMarkerClick(marker, result) {
    kakao.maps.event.addListener(marker, 'click', function () {
        // 팝업창 출력하기
        showModal(result.id)
    })
}

// 마커 위 호버 시 보여주는 인포윈도우
function displayInfowindow(marker, title) {
    var content = '<div class="info-window">' + title + '</div>';
    infowindow.setContent(content);
    infowindow.open(map, marker);
}