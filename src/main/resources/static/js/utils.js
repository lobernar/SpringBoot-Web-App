export function checkJWT(jwt) {
    if(jwt == null) {
        alert("You must log in first!");
        window.location.href = "/index.html";
    }
}