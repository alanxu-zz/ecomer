function post(url, data, method) {
    var form = document.createElement("form");
    form.setAttribute("action", url);
    if(method) {
        form.setAttribute("method", method);
    }

    if (data) {
        for (var k in data) {
            if (data.hasOwnProperty(k)) {
                var field = document.createElement("input");
                field.setAttribute("type", "hidden");
                field.setAttribute("name", key);
                field.setAttribute("value", data[key]);
                form.appendChild(field);
            }
        }   
    }

    document.body.appendChild(form);
    form.submit();

}