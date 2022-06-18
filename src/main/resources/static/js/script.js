let json = '';
let tagRows = [];
let tagSearch;
let searchBar;
let tagDisplay;
let topBar;
let leftBar;
let mainBody;
let createTag;
let createTagDiv;
let selectedTags = [];

let inputFocused = false;
function init() {
    tagRows = [... document.getElementsByClassName("tagRow")];
    tagSearch = document.getElementById("tagSearch");
    searchBar = document.getElementById("search");
    tagDisplay = document.getElementById("tagDisplay");
    topBar = document.getElementById("topBar");
    leftBar = document.getElementById("leftBar");
    mainBody = document.getElementById("mainBody");
    createTag = document.getElementById("mainBody");
    createTagDiv = document.getElementById("createTagDiv");
    json = [... JSON.parse(searchBar.dataset.model)];

    addEventListeners();
}

function addEventListeners() {
    [...document.getElementsByClassName("listPart")].forEach(element => element.addEventListener('click', () => listItemListener(element)));
    [...document.getElementsByClassName("rowContent")].forEach(element => element.addEventListener('click', () => handleRowClick(element)));
    searchBar.addEventListener('keyup', () => handleSearchBar());
    searchBar.addEventListener('focus', () => focusSearchBar());
    topBar.addEventListener('click', (e) => blurSearchBar(e));
    leftBar.addEventListener('click', (e) => blurSearchBar(e));
    mainBody.addEventListener('click', (e) => blurSearchBar(e));
    createTag.addEventListener('click', (e) => showSideBar(e));
}

function showSideBar(event) {
    if(event.target === createTag) {
        fetch('/viewAttributes')
            .then(res => {
                console.log(res);
                return res.text()
            })
            .then(text => {
                console.log(text);
                const sideBar = document.getElementById("rightSideBar");
                console.log(sideBar);
                sideBar.innerHTML = text;
                sideBar.classList.remove('hidden');
            });
    }
    else
    {
        const id = event.target.dataset.id;
        fetch('/viewAttributes?id=' + id)
            .then(res => res.text())
            .then(text => {
                const sideBar = document.getElementById("rightSideBar");
                sideBar.innerHTML = text;
                sideBar.classList.remove('hidden');
            });
    }

}

function listItemListener(element) {
    const listContent = element.querySelector("div.listcontent");

    if (listContent.classList.contains('none'))
        listContent.classList.remove('none');
    else
        listContent.classList.add('none');
}
function focusSearchBar() {
    inputFocused = true;
    tagSearch.style.display = '';
}
function blurSearchBar(e) {
    if(document.activeElement !== searchBar)
        tagSearch.style.display = 'none';
}
function handleSearchBar() {
    const value = searchBar.value

    let results = json.filter(obj => obj.name.indexOf(value) !== -1)
        .map(obj => obj.id);

    tagRows.forEach(row => {
        let id = row.dataset.id;
        if(results.indexOf(Number.parseInt(id)) != -1)
            row.style.display = '';
        else
            row.style.display = 'none';
    });
}

function handleRowClick(row){
    console.log(row)
    if(row.dataset.type === 'tag') {
        tagSearch.style.display = 'none';
        const id = row.dataset.id;
        const tagInfo = json.filter(obj => obj.id === Number.parseInt(id))[0]
        const div = document.createElement("div");
        div.className = 'displayTag big selectable';
        div.style.border = '3px solid #' + tagInfo['color'];
        div.style.backgroundColor = '#' + tagInfo['color'];
        div.title = tagInfo['name'];
        div.addEventListener("click", () => removeTagFromSearch(tagInfo.id, div))

        selectedTags.push(tagInfo['id']);
        tagDisplay.appendChild(div);
        filterRows();
    }
    if(row.dataset.type === 'track') {

    }
}

function removeTagFromSearch(id, div) {
    selectedTags = selectedTags.filter(obj => obj !== Number.parseInt(id));
    div.parentElement.removeChild(div);
    console.log(Number.parseInt(id));
    console.log(selectedTags);
    filterRows();
}

function filterRows() {
    console.log(selectedTags);
    [... document.getElementsByClassName("trackRow")].forEach(row => {
        let originalLength = row.dataset.tags;

        let rowTagids = row.dataset.tags.split(";").map(id => Number.parseInt(id));
        let filteredTags = selectedTags.filter(selectedTag => rowTagids.indexOf(selectedTag) == -1);

        if(filteredTags.length == 0)
            row.style.display = '';
        else
            row.style.display = 'none';
    })
}

function fetchPlaylists() {
    console.log('fetching tracks...')
    fetch("/tracks")
        .then(res => res.text())
        .then(htmlText => document.getElementById("tracks").innerHTML = htmlText);
}

function showTrackInfo(element) {
    const trackJson = JSON.parse(element.dataset.model);
    console.log(trackJson);
    const title = trackJson.name;
    const html = document.getElementById(trackJson.id + '-feature').innerHTML;

    showPopup(title, body);
}

function createTagDialog() {
    const title = "Create new Tag";
    const html = createTagDiv.innerHTML;
    showPopup(title, html);
}

function showPopup(title, body) {
    document.getElementById("popup").style.display = '';
    document.getElementById("mainContent").classList.add('unfocused')
    document.getElementById("popupTitle").innerText = title;
    document.getElementById("popupBody").innerHTML = body;
}

function closePopup(){
    document.getElementById("popup").style.display = 'none';
    document.getElementById("mainContent").classList.remove('unfocused')
}

function setValue(element) {
    const id = element.id;
    const value = element.value;
    console.log(value);
    document.getElementById(id + "Value").innerText = value;
}

function ensureValueIsLessThan(element, elementToCheck) {
    if(element.value >= elementToCheck.value || (element.value == 100 && elementToCheck.value != 100))
        element.value = elementToCheck.value;
}

function ensureValueIsMoreThan(element, elementToCheck) {
    if(element.value <= elementToCheck.value)
        element.value = elementToCheck.value;
}

function editName(id){
    const nameElement = document.getElementById('name-' + id);
    const editElement = document.getElementById('input-' + id);
    editElement.children[0].value = nameElement.innerText

    nameElement.style.display = 'none'
    editElement.style.display = '';
    editElement.children[0].focus();
}

function finishEditName(id){
    const nameElement = document.getElementById('name-' + id);
    const editElement = document.getElementById('input-' + id);

    nameElement.innerText = editElement.children[0].value;

    nameElement.style.display = ''
    editElement.style.display = 'none'
}

function deleteName(id){
    document.getElementById('id');
}