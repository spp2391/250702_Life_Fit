const schoolApiKey = "4be57f8be2a5cfb3ec15fc2c91db959d";
const gubuns = ["elem_list", "midd_list", "high_list", "univ_list", "seet_list", "alte_list"];

async function searchSchool(gubun) {
    var allData = [];
    const url = `https://www.career.go.kr/cnet/openapi/getOpenApi`
        + `?apiKey=${schoolApiKey}`
        + `&svcType=api`
        + `&svcCode=SCHOOL`
        + `&contentType=json`
        + `&region=100267`
        + `&gubun=${gubun}`
        + `&thisPage=1`
        + `&perPage=500`;

    try {
        const res = await fetch(url);
        const json = await res.json();
        const list = json?.dataSearch?.content || [];
        allData.push(...list);
    } catch (err) {
        console.error("오류:", err);
    }
    var result = [];
    for (key of allData) {
        // 주소에 괄호가 있을 경우 괄호 삭제
        // if (key.adres.indexOf("(") !== -1) {
        //     key.adres = key.adres.slice(0, key.adres.indexOf('('));
        // }
        result.push({
            place_name: key.schoolName,
            address_name: key.adres
        })
    }
    console.log(result);
    return result;
}

// const policeServiceKey = 'M8WULWL5-M8WU-M8WU-M8WU-M8WULWL54M';
//
// async function fetchPoliceXmlPage(pageNo) {
//     const url = `https://safemap.go.kr/openApiService/data/getSecurityFacilityData.do?serviceKey=${policeServiceKey}&pageNo=${pageNo}&numOfRows=1000&type=xml`;
//     const res = await fetch(url);
//     const text = await res.text();
//     return new window.DOMParser().parseFromString(text, "application/xml");
// }
//
// function getPoliceValue(item, tag) {
//     const el = item.getElementsByTagName(tag)[0];
//     return el ? el.textContent.trim() : '-';
// }
//
// async function searchSecurity() {
//     let page = 1;
//     let totalLoaded = 0;
//     let result = [];
//     while (true) {
//         const xml = await fetchPoliceXmlPage(page);
//         const items = xml.getElementsByTagName("item");
//         if (items.length === 0) break;
//         for (let item of items) {
//             const police = getPoliceValue(item, "POLICE");
//             const fcltyNm = getPoliceValue(item, "FCLTY_NM");
//             const addr = getPoliceValue(item, "RN_ADRES");
//
//             // 부산 지역만 출력
//             if (police.includes("부산")) {
//                 result.push({
//                     place_name: fcltyNm,
//                     address_name: addr
//                 })
//                 // console.log(fcltyNm);
//                 totalLoaded++;
//             }
//         }
//         page++;
//     }
//     return result;
//
// }
//
// searchPolice().catch(err => {
//     console.error("❌ 오류 발생:", err);
//     status.textContent = "❌ 데이터 로딩 실패 (CORS 또는 인증키 문제일 수 있음)";
// });

async function searchSecurity() {
    let result = [];
    return fetch ("./SearchSecurity.json")
        .then(response => response.json())
        .then(json => {
            console.log(json);
            result = json;
            return result;
        })
}

async function searchSubway(){
    let result = [];
    return fetch("/api/stations")
        .then(response => response.json())
        .then(json => {
            console.log(json);
            result = json;
            return result;
        })
}