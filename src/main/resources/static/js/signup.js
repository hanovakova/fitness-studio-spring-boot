function signup(classId, button, contextPath, event) {
    event.preventDefault();
    const form = button.closest("form");
    const formData = new FormData(form);

    fetch(contextPath + "/viewClasses", {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Request failed: " + response.status);
            }
            return response.json();
        })
        .then(data => {
            if (data.status === "success") {
                button.disabled = true;
                button.textContent = "Signed Up";
            }
        })
        .catch(error => console.error("Error:", error));
}