const list = document.getElementById('usersList');
const items = list.querySelectorAll("li");
console.log(items)
items.forEach(function (item) {
    item.addEventListener("click", function() {
        const current = document.getElementsByClassName("selected");
        if (current.length > 0) {
            current[0].className = current[0].className.replace(" selected", "");
        }
        this.className += " selected";
    });
})
// for (let i = 0; i < items.length; i++) {
//     items[i].addEventListener("click", function() {
//         const current = document.getElementsByClassName("selected");
//         if (current.length > 0) {
//             current[0].className = current[0].className.replace(" selected", "");
//         }
//         this.className += " selected";
//     });
// }