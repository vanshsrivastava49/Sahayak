document.getElementById('fetchMessage').addEventListener('click', function() {
    fetch('http://localhost:8080/api/login')
        .then(response => response.text())
        .then(data => {
            document.getElementById('message').innerText = data;
        })
        .catch(error => console.error('Error:', error));
});