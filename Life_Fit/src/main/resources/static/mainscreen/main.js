// 사이드바 팝업 스크린
var sidebarStatus = 0;
function sidebarPopup(status) {
    var popup;
    if (status === sidebarStatus) {
        sidebarStatus = 0;
    } else {
        if (sidebarStatus === 1) {
            popup = document.getElementById('popup-category');
            popup.classList.toggle('popup-show');
        } else if (sidebarStatus === 2) {
            popup = document.getElementById('popup-keyword');
            popup.classList.toggle('popup-show');
        }
        sidebarStatus = status;
    }
    if (status === 1) {
        popup = document.getElementById('popup-category');
        popup.classList.toggle('popup-show');
    } else if (status === 2) {
        popup = document.getElementById('popup-keyword');
        popup.classList.toggle('popup-show');
    }
}

// TODO: 상세보기 팝업 작성
function showMarkerInfo(position) {
    var popupInfo = document.getElementById('popup-info');
    popupInfo.classList.toggle('popup-show');

    // TODO: position을 infoUrl로 바꾸도록 검색 (키워드 검색).
    // TODO: 하나의 position에 여러 infoUrl이 있을 경우, 각 infoUrl에 대해 팝업을 하나씩 띄움.
    // TODO: infoUrl을 파싱해서 정보를 가져옴.
    // TODO: 여러번 infoUrl을 검색할 필요 없게 페이지에 있는 동안 임시로 position: infoUrl을 저장함.
    var infoUrl = position;

    if (infoUrl !== null) {
        var popupInfoTitle = document.getElementById('popup-info-title');
        popupInfoTitle.innerText = "";
        var popupInfoLocation = document.getElementById('popup-info-location');
        popupInfoLocation.innerText = "";
        var popupInfoUrl = document.getElementById('popup-info-url');
        popupInfoUrl.setAttribute("href", infoUrl);
    }
}


// TODO: 키워드 검색 함수 제작

function search() {
    // 키워드와 카테고리를 읽는다.
    var keyword = document.getElementById('popup-keyword-keyword-search').value;
    var category = document.querySelector('#popup-keyword input[type="radio"][name="category"]:checked')?.value;
    var area= "부산 부산진구";
    console.log('keyword: '+keyword);
    console.log('category: '+category);
    console.log('area: '+area);

    // TODO: 부산 좌표값을 변수로 작성


    return false;
}

// function keywordKakaoSearch(keyword, options) {
//     // TODO: 카카오맵 API에서 키워드 검색을 실행해 변수로 저장.
//     const places = new kakao.maps.services.Places();
//     var kakaoResultList;
//     const callback = function(result, status) {
//         console.log(result);
//         console.log(status);
//         console.log(kakao.maps.services.Status.OK);
//         if (status === kakao.maps.services.Status.OK) {
//
//             kakaoResultList = result;
//             // 카카오맵 API의 검색결과에서 부산에 있는 값만을 추려냄.
//             // kakaoResultList = filterByState(kakaoResultList, "부산");
//
//             // TODO: 공공데이터포털 API서 카테고리에 맞는 모든 값을 불러옴.
//             // TODO: 공공데이터포털 검색결과에서 주소와 키워드에 맞는 값만을 추려냄.
//             // TODO: 카카오맵 API와 공공데이터포털 API의 주소가 중복되지 않도록 모든 값을 추려냄.
//
//             return kakaoResultList;
//         } else {
//             return [];
//         }
//     };
//     places.keywordSearch(keyword, callback, options);
//     return result;
// }



// TODO: 지역별 중심좌표 + 원형 넓이 구해서 딕셔너리로 저장



// TODO: 지오코딩 함수 제작
var geocoder = new kakao.maps.services.Geocoder();




// TODO: 마커 표시
var markers = [];
function addMarker(position) {
    console.log(position);
    geocoder.addressSearch(position, function(result, status){
        if (status === kakao.maps.services.Status.OK) {
            var latLng = new kakao.maps.LatLng(result[0].y, result[0].x);
            console.log('latLng: '+latLng);
            var marker = new kakao.maps.Marker({
                position: latLng
            });
            // TODO: addListener vs addEventListener 알아보기
            // kakao.maps.event.addListener(marker, 'click', function() {
            //     showMarkerInfo(position);
            // });
            // marker.addEventListener('click', function () {
            //     showMarkerInfo(position);
            // })
            marker.setMap(map);
            markers.push(marker);
        }
    })
}

function removeMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}



// 카카오맵 API 주소별로 지역 구분하는 함수
function filterByState(mapJson, area) {
    var result = [];
    for (key of mapJson) {
        if (key.address_name.includes(area)) {
            result.push(key);
        }
    }
    return result;
}




