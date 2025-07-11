const apiKey = "4be57f8be2a5cfb3ec15fc2c91db959d";
const gubuns = ["elem_list", "midd_list", "high_list", "univ_list", "seet_list", "alte_list"];

async function searchSchool(gubun) {
    var allData = [];
    const url = `https://www.career.go.kr/cnet/openapi/getOpenApi`
        + `?apiKey=${apiKey}`
        + `&svcType=api`
        + `&svcCode=SCHOOL`
        + `&contentType=json`
        + `&region=100267`
        + `&gubun=${gubun}`
        + `&thisPage=1`
        + `&perPage=100`;

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
        result.push({
            place: key.schoolName,
            address: key.adres
        })
    }
    console.log(result);
    return result;
}