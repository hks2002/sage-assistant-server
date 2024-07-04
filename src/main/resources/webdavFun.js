/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2024-07-01 17:46:12                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-07-04 13:30:27                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

function debounce(fn, wait = 250, immediate) {
  let timer = null;

  function debounced(/* ...args */) {
    const args = arguments;

    const later = () => {
      timer = null;
      if (immediate !== true) {
        fn.apply(this, args);
      }
    };

    if (timer !== null) {
      clearTimeout(timer);
    } else if (immediate === true) {
      fn.apply(this, args);
    }

    timer = setTimeout(later, wait);
  }

  debounced.cancel = () => {
    timer !== null && clearTimeout(timer);
  };

  return debounced;
}

function renderFileSize(sizeInBytes) {
  const units = ["B", "KB", "MB", "GB", "TB"];
  let unitIndex = 0;

  while (sizeInBytes >= 1024 && unitIndex < units.length - 1) {
    sizeInBytes /= 1024;
    unitIndex++;
  }

  return `${sizeInBytes.toFixed(1)} ${units[unitIndex]}`;
}

function updateDocumentList(data) {
  if (data.length === 0) {
    document.getElementById("documentList").innerHTML = "No document found";
  } else {
    var html = "<tbody><tr>";
    html += "<td align='left'><font size='+1'><strong>Filename</strong></font></td>";
    html += "<td align='center'><font size='+1'><strong>Size</strong></font></td>";
    html += "<td align='right'><font size='+1'><strong>Last Modified</strong></font></td>";
    html += "</tr>";
    var shade = false;
    for (var i = 0; i < data.length; i++) {
      if (shade) {
        html += "<tr bgcolor='#eeeeee'>";
      } else {
        html += "<tr>";
      }
      shade = !shade;
      html += `<td align='left'>&nbsp;&nbsp;<a href='javascript:void(0)' onclick="sendRequest('/docs/${data[i].location}/${encodeURIComponent(data[i].file_name)}')"><tt>${
        data[i].file_name
      }</tt></a></td>`;
      html += `<td align='right'>${renderFileSize(data[i].size)}</td>`;
      html += `<td align='right'><tt>${data[i].doc_modified_at}</tt></td>`;
      html += "</tr>";
    }
    html += "</tbody>";
    document.getElementById("documentList").innerHTML = html;
  }
}

document.getElementById("search").addEventListener(
  "input",
  debounce(function (e) {
    if (e.target.value.length < 3) {
      return;
    }

    fetch("/Data/GetDocsInfo" + "?Pn=" + e.target.value)
      .then((res) => res.json())
      .then((data) => {
        updateDocumentList(data);
      });
  }, 1500)
);

document.getElementById("bpCode").addEventListener(
  "input",
  debounce(function (e) {
    if (e.target.value.length !== 5) {
      return;
    } else {
      setCookie("bpCode", e.target.value, 60 * 60 * 24);
    }
  }, 1000)
);

function setCookie(name, value, seconds) {
  seconds = seconds || 0;
  var expires = "";
  if (seconds != 0) {
    var date = new Date();
    date.setTime(date.getTime() + seconds * 1000);
    expires = "; expires=" + date.toGMTString();
  }
  document.cookie = name + "=" + value + expires + "; path=/";
}

function getCookie(name) {
  var nameEQ = name + "=";
  var ca = document.cookie.split(";");
  for (var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == " ") {
      c = c.substring(1, c.length);
    }
    if (c.indexOf(nameEQ) == 0) {
      return c.substring(nameEQ.length, c.length);
    }
  }
  return false;
}

function clearCookie(name) {
  setCookie(name, "", -1);
}

function sendRequest(url) {
  var bp = document.getElementById("bpCode").value;
  var isDirectory = false;
  if (url.endsWith("/")) {
    isDirectory = true;
  }
  if (bp !== "") {
    url = url + "?bpCode=" + bp;
    setCookie("bpCode", bp, 60 * 60 * 24);
  } else {
    clearCookie("bpCode");
  }
  if (isDirectory) {
    window.location.href = url;
  } else {
    window.open(url);
  }
}

window.onload = function () {
  var bp = getCookie("bpCode");
  if (bp !== false) {
    document.getElementById("bpCode").value = bp;
  }
};
