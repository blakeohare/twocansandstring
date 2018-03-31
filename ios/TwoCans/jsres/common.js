function getHtmlElement(id) {
    var e = document.getElementById(id);
    if (!e) return null;
    return e;
}

function htmlspecialchars(s) {
    var output = [];
    var length = s.length;
    var c;
    for (var i = 0; i < length; ++i) {
        c = s[i];
        switch (c) {
            case '<': output.push('&lt;'); break;
            case '>': output.push('&gt;'); break;
            case '&': output.push('&amp;'); break;
            case '"': output.push('&quot;'); break;
            default: output.push(c); break;
        }
    }
    return output.join('');
}


