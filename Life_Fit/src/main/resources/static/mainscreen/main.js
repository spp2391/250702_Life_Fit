// 사이드바 팝업 스크린
var sidebarStatus = 0;
function sidebarPopup(status) {
    var popup = document.getElementById('popup-keyword');

    let popupKeyword = document.querySelector("#popup-keyword-keyword");
    let popupArea = document.querySelector("#popup-area");
    let keywordSidebar = document.querySelector('#main-sidebar-1');
    let categorySidebar = document.querySelector('#main-sidebar-3');
    let keywordSearchButton = document.querySelector("#keywordSearchButton");
    let categorySearchButton = document.querySelector("#categorySearchButton");
    // 사이드바 종료
    if (popup.classList.contains('popup-show') && status===sidebarStatus) {
        popup.classList.remove('popup-show');
        sidebarStatus = 0;
        return;
    }
    // 키워드검색
    if (status === 1) {
        // keywordSidebar.style.background="#ddd";
        // categorySidebar.style.background="none";
        popupKeyword.style.display="block";
        popupArea.style.display="none";
        keywordSearchButton.style.display="inline-block";
        categorySearchButton.style.display="none";
        sidebarStatus = 1;
    //카테고리 검색
    } else if (status === 2) {
        // keywordSidebar.style.background="none";
        // categorySidebar.style.background="#ddd";
        popupKeyword.style.display="none";
        popupArea.style.display="block";
        keywordSearchButton.style.display="none";
        categorySearchButton.style.display="inline-block";
        sidebarStatus = 2;
    }
    // 카테고리 내용 설정
    setCategoryTable(1);
    popup.classList.add('popup-show');
}
// 카카오 카테고리 종류
const categoryTypes = {
    MT1: "대형마트",CS2: "편의점",PS3: "어린이집</br>유치원",
    SC4: "학교",AC5: "학원",PK6: "주차장",
    OL7: "주유소</br>충전소",SW8: "지하철역",BK9: "은행",
    CT1: "문화시설",AG2: "중개업소",PO3: "공공기관",
    AT4: "관광명소",AD5: "숙박",FD6: "음식점",
    CE7: "카페",HP8: "병원",PM9: "약국"
};
// 카테고리 종류
const categoryList = ["지하철","버스","병원/약국","초등학교","중학교","고등학교","학교","치안시설"];

// 카테고리 설정
function setCategoryTable(status){
    let categoryTable = document.getElementById("popup-keyword-table");
    let str = "";
    let data = status === 1 ?  categoryTypes : categoryList;
    let index = 0;
        Object.entries(data).forEach(([key, value]) => {
            if(index===0){ str += "<tr>";}
            str += "<td >";
            str += `<label for="popup-keyword-category-option-${key}">${value}<input onclick='selectCategory(event)' type="radio" id="popup-keyword-category-option-${key}" name="category" value="${key}"/></label>`
            str += "</td>";
            index++;
            if(index>=5){
                index = 0;
                str += "</tr>";
            }
        });
    categoryTable.innerHTML = str;
}
// 카테고리 검색에서 선택 시 색상 변경.
function selectCategory(e) {
    e.stopPropagation();
    let allCategoryBox = document.querySelectorAll("#popup-keyword-table label");
    allCategoryBox.forEach((item) => {
        item.style.backgroundColor="white";
        item.style.color = "black";
    })
    let categoryBox = e.target;
    categoryBox.parentNode.style.backgroundColor="#007BFF";
    categoryBox.parentNode.style.color = "white";
    console.log(categoryBox.id, "clicked");
}
// 폼 리셋 시 모든 색상 원상복구
function clearForm(e) {
    let allCategoryBox = document.querySelectorAll("#popup-keyword-table label");
    allCategoryBox.forEach((item) => {
        item.style.backgroundColor="white";
        item.style.color = "black";
    })
}
// 카카오API 검색의 콜백 함수
var callback = function (result, status) {
    if (status === kakao.maps.services.Status.OK) {
        // 검색 성공시 결과 저장.
        console.log(result);
        // saveResult(result);
        // 모든 마커 제거.
        removeMarker();
        // 검색 결과에 맞춰 마커 세팅.
        setMarkers(result);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert('검색 결과가 없습니다.');
        console.log('검색 결과가 없습니다.');
    } else if (status === kakao.maps.services.Status.ERROR) {
        alert('오류가 발생했습니다.');
    }
}
// 키워드 검색 버튼 실행
document.querySelector("#keywordSearchButton").addEventListener("click", function(e){
    e.preventDefault();
    e.stopPropagation();
    let keyword = document.searchForm.keyword.value;
    let category = document.searchForm.category.value;
    if(category){
        // 키워드, 카테고리 포함 검색
        places.keywordSearch(keyword, callback, {location:map.getCenter(), category_group_code:category});
    }else{
        // 키워드만 검색
        places.keywordSearch(keyword, callback, {location:map.getCenter()});
    }
});
// 카테고리 검색
document.querySelector("#categorySearchButton").addEventListener("click", function(e){
    e.preventDefault();
    e.stopPropagation();
    let category = document.searchForm.category.value;
    let area = document.searchForm.area.value;
    if(area){
        let lat = areaCoordinates[`부산 ${area}`].y;
        let lng = areaCoordinates[`부산 ${area}`].x;
        places.categorySearch(category, callback, {location :new kakao.maps.LatLng(lat, lng)});
    }else{
        places.categorySearch(category, callback, {bounds : map.getBounds()});
    }

});




// 상세보기 팝업 작성
var lastSelectedIndex = -1;
// var markerInfo = null;
function showMarkerInfo(markerInfo, favoriteRespData) {
    console.log('result', markerInfo);
    let favoriteButton = "";
    if (favoriteRespData === "Not logged in") {
        favoriteButton = "";
    } else if (favoriteRespData === "Favorited") {
        favoriteButton = `<div class="close" id="addFavorites" style="color:#007BFF; background-color:#bbf;" onclick="removeFavorites()" title="즐겨찾기">★</div>`;
    } else if (favoriteRespData === "Not Favorited") {
        favoriteButton = `<div class="close" id="addFavorites" onclick="saveFavorites()" title="즐겨찾기">★</div>`;
    }

    return '<div id="popup-info" class="popup-info">'
        + '<div class="place-name"><div class="title">'
        + markerInfo.place_name
        + '</div>'
        + favoriteButton
        + '<div class="close" onclick="closeOverlay()" title="닫기">X</div>'
        + '</div>'
        + '<div class="address-name">'
        + markerInfo.address_name
        + '</div>'
        + '<div class="category-name">'
        + markerInfo.category_name
        + '</div>'
        + '<div class="place-url"><a href="' + markerInfo.place_url + '">' + markerInfo.place_url + '</a></div>'
        + '</div>';
}

function saveFavorites() {
    fetch(`/api/mainscreen/favorite`,{
        method:'POST', // GET, POST, PUT, DELETE
        // JSON형식의 데이터를 서버에 전송하는 설정
        headers:{"content-Type":"application/json"},
        // JSON.stringify(자바스크립트 객체) : 자바스크립트 객체를 JSON문자열로 변경
        body : JSON.stringify({
            title:document.querySelector("#popup-info .title").innerText,
            address:document.querySelector("#popup-info .address-name").innerText,
            description:document.querySelector("#popup-info .category-name").innerText,
            url:document.querySelector("#popup-info .place-url").innerText
        })
    }).then(()=>{
        // 즐겨찾기 설정 완료 후 즐겨찾기 버튼 변경.
        let favoriteButton = document.querySelector("#addFavorites");
        favoriteButton.setAttribute("style","color:#007BFF; background-color:#bbf;");
        favoriteButton.setAttribute("onclick","removeFavorites()");
        alert('즐겨찾기 등록 완료되었습니다.');
    })
}

function removeFavorites() {
    fetch(`/api/mainscreen/favorite/delete`,{
        method:'DELETE',
        headers:{"content-Type":"application/json"},
        body : JSON.stringify({
            url:document.querySelector("#popup-info .place-url").innerText,
        })
    }).then(()=>{
        // 즐겨찾기 해제 완료 후 즐겨찾기 버튼 변경.
        let favoriteButton = document.querySelector("#addFavorites");
        favoriteButton.setAttribute("style","color:black; background-color:white;");
        favoriteButton.setAttribute("onclick","saveFavorites()");
        alert('즐겨찾기 제거 완료되었습니다.');
    })
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
var bounds = new kakao.maps.LatLngBounds();
var infowindow = new kakao.maps.InfoWindow({zIndex:10,disableAutoPan:true});

// TODO: 행정지역별로 중심좌표와 대략적 반지름 파악.
var areaCoordinates = {
    "부산 중구": {
        x: 129.0345083,
        y: 35.10321667,
        r: 0.05
    },
    "부산 서구": {
        x: 129.0263778,
        y: 35.09483611,
        r: 0.05
    },
    "부산 동구": {
        x: 129.059175,
        y: 35.13589444,
        r: 0.05
    },
    "부산 영도구": {
        x: 129.0701861,
        y: 35.08811667,
        r: 0.05
    },
    "부산 부산진구": {
        x: 129.0553194,
        y: 35.15995278,
        r: 0.05
    },
    "부산 동래구": {
        x: 129.0858556,
        y: 35.20187222,
        r: 0.05
    },
    "부산 남구": {
        x: 129.0865,
        y: 35.13340833,
        r: 0.05
    },
    "부산 북구": {
        x: 128.992475,
        y: 35.19418056,
        r: 0.05
    },
    "부산 해운대구": {
        x: 129.1658083,
        y: 35.16001944,
        r: 0.05
    },
    "부산 사하구": {
        x: 128.9770417,
        y: 35.10142778,
        r: 0.05
    },
    "부산 금정구": {
        x: 129.0943194,
        y: 35.24007778,
        r: 0.05
    },
    "부산 강서구": {
        x: 128.9829083,
        y: 35.20916389,
        r: 0.05
    },
    "부산 연제구": {
        x: 129.082075,
        y: 35.17318611,
        r: 0.05
    },
    "부산 수영구": {
        x: 129.115375,
        y: 35.14246667,
        r: 0.05
    },
    "부산 사상구": {
        x: 128.9933333,
        y: 35.14946667,
        r: 0.05
    },
    "부산 기장군": {
        x: 129.2222873,
        y: 35.24477541,
        r: 0.05
    }
};

// 검색 결과를 searchHistory에 저장.
// 검색 직후 결과가 나오기 전에 다음 검색을 시도하면 잘못된 인덱스에 결과가 기록될 위험이 있음.
// TODO: 다른 기능이 모두 완료되었을 경우 검색 도중 다른 검색을 시도하지 못하도록 잠금
function saveResult(result) {
    searchHistory[searchHistory.length-1].result = result;
}

// 마커 설정. (다수)
function setMarkers(result) {
    resetBounds();
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
        (function (marker, title, result) {
            setMarkerHover(marker, title);
            // 마커를 클릭할 시 상세보기 페이지가 보이도록 설정.
            if (result.address_name) {
                setMarkerClick(marker, result)
            }
        })(marker, title, result[i]);
        // 마커 리스트에 마커 추가.
        markers.push(marker);
    }
    // TODO: 지도를 검색결과가 보이는 곳으로 이동.
    map.setBounds(bounds);
}
// 마커 설정 (단일)
function setMarker(result) {
    // 마커의 위치, 이름 설정.
    let position = new kakao.maps.LatLng(result.y, result.x);
    let title = result.place_name;
    // 마커의 바운더리 설정.
    bounds.extend(position);
    // 마커 세팅.
    var marker = new kakao.maps.Marker({
        map: map,
        position: position,
        title: title,
        zIndex: 1
    });
    setMarkerHover(marker, title);
    if (result.address_name) {
        setMarkerClick(marker, result)
    }
    markers.push(marker);
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
        // 장소의 고유 ID를 가져옴 (http://place.map.kakao.com/321220117에서 321220117)
        const place_id = result.place_url.split("/")[3];
        // 로그인 여부, 즐겨찾기 여부 확인.
        fetch(`/api/mainscreen/favorites/${place_id}`,{
            method:'GET', // GET, POST, PUT, DELETE
        }).then(async (favoriteResp) => {
            // favoriteRespData: Not logged in / Favorited / Not Favorited 중 하나를 저장.
            let favoriteRespData = await favoriteResp.json();
            favoriteRespData = favoriteRespData.data;
            console.log('res', favoriteRespData);
            // 클릭된 장소명을 출력.
            console.log('clicked', result.place_name);
            // lastSelectedIndex의 기본값은 -1. -1일 경우에 팝업이 닫혀있으며, 다른 경우에 열림.
            if (lastSelectedIndex === result.address_name) {
                // 현재 열려 있는 팝업의 인덱스랑 클릭한 마커가 같으면 팝업을 닫음.
                closeOverlay();
                lastSelectedIndex = -1;
            } else {
                if (lastSelectedIndex !== -1) {
                    // 만약 마커의 값이 -1이 아니라면 팝업을 닫음.
                    closeOverlay();
                }
                // 다를 경우 클릭한 마커의 값으로 팝업을 세팅함
                // var content = '<div style="background-color: white; z-index: 10;">Hello World</div>';
                var content = showMarkerInfo(result, favoriteRespData);
                var position = new kakao.maps.LatLng(result.y, result.x);
                var markerInfo = new kakao.maps.CustomOverlay({
                    clickable: true,
                    content: content,
                    position: position,
                    xAnchor: 0.5,
                    yAnchor: 1,
                    zIndex: 5
                });
                console.log('markerInfo', markerInfo);
                console.log('marker', marker);
                markerInfo.setMap(map);
                var element = document.getElementById('popup-info');
                element.parentNode.style.pointerEvents = 'none';
                element.style.pointerEvents = 'auto';
                lastSelectedIndex = result.address_name;
            }
        })
    })
}

// 마커 위 호버 시 보여주는 인포윈도우
function displayInfowindow(marker, title) {
    var content = '<div class="info-window">' + title + '</div>';
    infowindow.setContent(content);
    infowindow.open(map, marker);
}
// 상세보기 팝업 popup-info 오버레이를 닫음.
function closeOverlay() {
    let popup = document.getElementById('popup-info');
    if (popup) {
        popup.remove();
    }
    lastSelectedIndex = -1;
}