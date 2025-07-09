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
function showMarkerInfo(index) {
    return new kakao.maps.CustomOverlay({
        map: map,
        clickable: true,
        content: '',
        position: '',
        
    });
}

/*
    아래는 검색기능.
    입력한 변수의 종류에 따라 키워드 검색/카테고리 검색을 시행.
    키워드 검색은 카카오API를 사용.
    카테고리 검색은 공공데이터포털을 이용.

    검색하기 이전에, 검색 조건이 시스템과 부합하는지 확인.
    아닐 경우 즉시 검색 종료.

    검색하기 이전에, 검색 조건이 기존 검색 히스토리에 있는지 검사.
    검색 히스토리에 있을 경우, 그 검색 히스토리가 가장 최근인지 확인.
    가장 최근일 경우 즉시 검색 종료.
    가장 최근이지 않을 경우 그 검색 히스토리의 인덱스를 마지막으로 옮김.
    또한 검색 과정을 스킵하고 마커 표시 단계로 이동.

    키워드 검색한 이후, 그 결과 전체(Array)를 검색 히스토리에 저장.
    그 후, 마커 표시 단계로 이동.

    카테고리 검색한 이후, 그 결과 중 에리어에 맞는 값만을 반환.
    그 후, 키워드가 있었을 경우 키워드에 맞는 값만을 반환.
    각 결과에 대해 키워드 검색을 실시. 키워드 검색의 결과 중 주소가 일치하는 값만을 검색 히스토리에 저장.
    그 후, 마커 표시 단계로 이동.

    마커 표시 시, 검색 히스토리에서 x좌표와 y좌표를 각각 가져오며, title에 장소 이름을 저장.
    마커 리스트에 마커 변수와 검색 결과 오브젝트를 저장.

    마커 클릭 시 마커 리스트에서 커스텀 오버레이를 작성.
    장소 이름, 주소, 즐겨찾기 버튼, 상세보기 사이트로 이동하는 html을 생성.

 */

// searchHistory: 검색 기록. 최근일수록 나중에 인덱스 존재.
// searchHistory 인덱스 형식: {keyword, category, area, result}
var searchHistory = [];
var markers = [];
var places = new kakao.maps.services.Places();
var infowindow = new kakao.maps.InfoWindow({zIndex:10});

// 메인 검색 함수.
function search() {
    // 키워드와 카테고리를 읽는다.
    var keyword = document.getElementById('popup-keyword-keyword-search').value;
    var category = document.querySelector('#popup-keyword input[type="radio"][name="category"]:checked')?.value;
    var area= "부산 부산진구";
    console.log('keyword: '+keyword);
    console.log('category: '+category);
    console.log('area: '+area);

    // 값이 제대로 작성되어있는지 확인.
    if (!keyword && !category) {
        alert('키워드나 카테고리를 선택하세요.')
        return false;
    } else if (category && !area) {
        alert('카테고리 검색 시 지역을 지정하세요.');
        return false;
    }

    // 검색 내용이 이전 검색 결과랑 중첩되는지 확인.
    for (var i = 0; i < searchHistory.length; i++) {
        if (searchHistory[i].keyword == keyword && searchHistory[i].category == category && searchHistory[i].area == area) {
            // 검색 내용이 가장 최근의 검색 결과인지 확인.
            if (i === searchHistory.length - 1) {
                // 그럴 경우 아무런 행동도 하지 않고 검색 종료.
                console.log('가장 최근의 검색 결과랑 중복. 검색 종료.')
                return false;
            } else {
                // 그렇지 않을 경우 result를 설정하고 마커를 설정 후, 검색 종료.
                console.log('중복되는 검색 결과 발견.')
                let result = searchHistory.splice(i, 1)[0];
                searchHistory.push(result);
                console.log('조정 후 검색 결과:',searchHistory)
                removeMarker();
                setMarker(result.result);
                return false;
            }
        }
    }
    // 중첩되는 결과가 아닐 경우 keyword, category, area를 모두 설정하여 요소 추가.
    searchHistory.push({
        keyword: keyword,
        category: category,
        area: area,
        result: []
    });

    if (keyword && !category) {
        keywordSearch(keyword);
    } else if (category && area) {
        categorySearch(keyword, category, area);
    }
    return false;
}

// 키워드 검색 함수.
function keywordSearch(keyword) {
    var callback = function (result, status) {
        if (status === kakao.maps.services.Status.OK) {
            // 검색 성공시 결과 저장.
            console.log(result);
            saveResult(result);
            // 모든 마커 제거.
            removeMarker();
            // 검색 결과에 맞춰 마커 세팅.
            setMarker(result);
        } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
            alert('검색 결과가 없습니다.');
        } else if (status === kakao.maps.services.Status.ERROR) {
            alert('오류가 발생했습니다.');
        }
    }
    places.keywordSearch(keyword, callback);
}

// TODO: 카테고리 일람 작성
function categorySearch(keyword, category, area) {
    return false;
}

// 검색 결과를 searchHistory에 저장.
// 검색 직후 결과가 나오기 전에 다음 검색을 시도하면 잘못된 인덱스에 결과가 기록될 위험이 있음.
// TODO: 다른 기능이 모두 완료되었을 경우 검색 도중 다른 검색을 시도하지 못하도록 잠금
function saveResult(result) {
    searchHistory[searchHistory.length-1].result = result;
}

// 모든 마커 삭제.
function removeMarker() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
}

// 마커 위 호버 시 마커 정보를 보여줌.
function displayInfowindow(marker, title) {
    var content = '<div style="padding:5px;z-index:2;">' + title + '</div>';
    infowindow.setContent(content);
    infowindow.open(map, marker);
}

// 마커 설정.
function setMarker(result) {
    var bounds = new kakao.maps.LatLngBounds();
    for (var i = 0; i < result.length; i++) {
        // 각 검색 결과에 대해 좌표와 장소명을 불러온다.
        let position = new kakao.maps.LatLng(result[i].y, result[i].x);
        let title = result[i].place_name;
        // LatLngBounds의 bounds를 검색결과에 따라 확장.
        bounds.extend(position);
        // 마커 세팅.
        var marker = new kakao.maps.Marker({
            map: map,
            position: position,
            title: title,
            zIndex: 1
        });
        (function (marker, title) {
            // 마커의 호버 이벤트 설정.
            kakao.maps.event.addListener(marker, 'mouseover', function() {
                displayInfowindow(marker, title);
            });
            kakao.maps.event.addListener(marker, 'mouseout', function() {
                infowindow.close();
            });
            // 마커를 클릭할 시 상세보기 페이지가 보이도록 설정.
            kakao.maps.event.addListener(marker, 'click', function () {
                showMarkerInfo(i);
            })
        })(marker, title);
        // 마커 리스트에 마커 추가.
        markers.push(marker);
    }
    // TODO: 지도를 검색결과가 보이는 곳으로 이동.
    map.setBounds(bounds);
}




























// TODO: 지역별 중심좌표 + 원형 넓이 구해서 딕셔너리로 저장



// TODO: 지오코딩 함수 제작
var geocoder = new kakao.maps.services.Geocoder();




// TODO: 마커 표시
// var markers = [];
// function addMarker(position) {
//     console.log(position);
//     geocoder.addressSearch(position, function(result, status){
//         if (status === kakao.maps.services.Status.OK) {
//             var latLng = new kakao.maps.LatLng(result[0].y, result[0].x);
//             console.log('latLng: '+latLng);
//             var marker = new kakao.maps.Marker({
//                 position: latLng
//             });
//             // TODO: addListener vs addEventListener 알아보기
//             // kakao.maps.event.addListener(marker, 'click', function() {
//             //     showMarkerInfo(position);
//             // });
//             // marker.addEventListener('click', function () {
//             //     showMarkerInfo(position);
//             // })
//             marker.setMap(map);
//             markers.push(marker);
//         }
//     })
// }

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




