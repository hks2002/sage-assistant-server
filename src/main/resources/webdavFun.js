/*****************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                    *
 * @CreatedDate           : 2024-07-01 17:46:12                              *
 * @LastEditors           : Robert Huang<56649783@qq.com>                    *
 * @LastEditDate          : 2024-07-22 15:21:41                              *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                  *
 ****************************************************************************/

function debounce(fn, wait = 250, immediate) {
    let timer = null

    function debounced(/* ...args */) {
        const args = arguments

        const later = () => {
            timer = null
            if (immediate !== true) {
                fn.apply(this, args)
            }
        }

        if (timer !== null) {
            clearTimeout(timer)
        } else if (immediate === true) {
            fn.apply(this, args)
        }

        timer = setTimeout(later, wait)
    }

    debounced.cancel = () => {
        timer !== null && clearTimeout(timer)
    }

    return debounced
}

function renderFileSize(sizeInBytes) {
    const units = ['B', 'KB', 'MB', 'GB', 'TB']
    let unitIndex = 0

    while (sizeInBytes >= 1024 && unitIndex < units.length - 1) {
        sizeInBytes /= 1024
        unitIndex++
    }

    return `${sizeInBytes.toFixed(1)} ${units[unitIndex]}`
}

function updateZHUDocumentList(data) {
    if (data.length === 0) {
        document.getElementById('docsInZHUList').innerHTML = '❗️No document found in Zhuhai server'
    } else {
        let html = ''
        let shade = false
        for (let i = 0; i < data.length; i++) {
            html += shade ? "<tr bgcolor='#eeeeee'>" : '<tr>'
            shade = !shade

            html += `<td align='left'>&nbsp;&nbsp;<a href='javascript:void(0)' onclick="sendRequest('/docs/${
                data[i].location
            }/${encodeURIComponent(data[i].file_name)}')">${data[i].file_name}</a></td>`
            html += `<td align='right'>${renderFileSize(data[i].size)}</td>`
            html += `<td align='right'>${data[i].doc_modified_at}</td>`
            html += '</tr>'
        }
        document.getElementById('docsInZHUList').innerHTML = html
    }
}

function updateDmsDocumentList(data) {
    if (data.length === 0) {
        document.getElementById('docsInDmsList').innerHTML = 'No document in New Sage DMS Audros server!❗️❗️❗️'
    } else {
        let html = '<tr>Documents in New Sage DMS Audros server (Will be downloaded automatically✔️):</tr>'
        let shade = false
        for (let i = 0; i < data.length; i++) {
            html += shade ? "<tr bgcolor='#eeeeee'>" : '<tr>'
            shade = !shade

            html += `<td align='left'>&nbsp;&nbsp;${data[i].file_name}</td>`
            html += `<td align='right'></td>`
            html += `<td align='right'></td>`
            html += '</tr>'
        }
        document.getElementById('docsInDmsList').innerHTML = html
    }
}

function updateTLSDocumentList(data) {
    if (data.length === 0) {
        document.getElementById('docsInTLSList').innerHTML = 'No document in Old file server!❗️❗️❗️'
    } else {
        let html = '<tr>Documents in Old file server(Need Manually download it⚠️):</tr>'
        let shade = false
        for (let i = 0; i < data.length; i++) {
            html += shade ? "<tr bgcolor='#eeeeee'>" : '<tr>'
            shade = !shade

            html += `<td align='left'>&nbsp;&nbsp;${data[i].file}</td>`
            html += `<td align='right'></td>`
            html += `<td align='right'></td>`
            html += '</tr>'
        }
        document.getElementById('docsInTLSList').innerHTML = html
    }
}

function setCookie(name, value, seconds) {
    seconds = seconds || 0
    let expires = ''
    if (seconds != 0) {
        let date = new Date()
        date.setTime(date.getTime() + seconds * 1000)
        expires = '; expires=' + date.toGMTString()
    }
    document.cookie = name + '=' + value + expires + '; path=/'
}

function getCookie(name) {
    const nameEQ = name + '='
    const ca = document.cookie.split(';')
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i]
        while (c.charAt(0) == ' ') {
            c = c.substring(1, c.length)
        }
        if (c.indexOf(nameEQ) == 0) {
            return c.substring(nameEQ.length, c.length)
        }
    }
    return false
}

function clearCookie(name) {
    setCookie(name, '', -1)
}

function sendRequest(url) {
    const bp = document.getElementById('bpCode').value
    let isDirectory = false
    if (url.endsWith('/')) {
        isDirectory = true
    }
    if (bp !== '') {
        url = url + '?bpCode=' + bp
        setCookie('bpCode', bp, 60 * 60 * 24)
    } else {
        clearCookie('bpCode')
    }
    if (isDirectory) {
        window.location.href = url
    } else {
        if (url.toUpperCase().endsWith('TIF') || url.toUpperCase().endsWith('TIFF')) {
            // window.open(url);
            var xhr = new XMLHttpRequest()
            xhr.responseType = 'arraybuffer'
            xhr.open('GET', url)
            xhr.onload = function (e) {
                if (xhr.responseType == 'application/pdf') {
                    window.open(url)
                } else {
                    const newDiv = document.createElement('div')
                    const deleteButton = document.createElement('button')
                    deleteButton.innerHTML = 'Close Tiff viewer'
                    deleteButton.addEventListener('click', function () {
                        newDiv.remove()
                        deleteButton.remove()
                    })
                    document.getElementsByTagName('h1')[0].appendChild(deleteButton)
                    document.getElementsByTagName('table')[0].before(newDiv)

                    var tiff = new Tiff({ buffer: xhr.response })
                    for (var i = 0, len = tiff.countDirectory(); i < len; ++i) {
                        tiff.setDirectory(i)
                        var canvas = tiff.toCanvas()
                        canvas.style.width = window.innerWidth - 50 + 'px'
                        newDiv.append(canvas)
                    }
                }
            }
            xhr.send()
        } else {
            window.open(url)
        }
    }
}

document.getElementById('search').addEventListener(
    'input',
    debounce(function (e) {
        if (e.target.value.length < 3) {
            return
        }

        fetch('/Data/GetDocsInfoFromZHU' + '?Pn=' + e.target.value)
            .then((res) => res.json())
            .then((data) => {
                updateZHUDocumentList(data)
            })

        fetch('/Data/GetDocsInfoFromDms' + '?Pn=' + e.target.value)
            .then((res) => res.json())
            .then((data) => {
                updateDmsDocumentList(data)
            })

        fetch('/Data/GetDocsInfoFromTLS' + '?Pn=' + e.target.value)
            .then((res) => res.json())
            .then((data) => {
                updateTLSDocumentList(data)
            })
    }, 1500)
)

document.getElementById('bpCode').addEventListener(
    'input',
    debounce(function (e) {
        if (e.target.value.length !== 5) {
            return
        } else {
            setCookie('bpCode', e.target.value, 60 * 60 * 24)
        }
    }, 1000)
)

window.onload = function () {
    const bp = getCookie('bpCode')
    if (bp !== false) {
        document.getElementById('bpCode').value = bp
    }
}

function uploadFile(file) {
    const formData = new FormData()
    formData.append('file', file) // 'file' 是服务器端用来接收文件的字段名，可以根据实际情况调整

    fetch('/Data/FileUpload', {
        method: 'POST',
        body: formData
    })
        .then((response) => {
            if (response.ok) {
                return response.json()
            } else {
                if (response.status === 401) {
                    return { success: false, msg: 'Login Required!' }
                }
                if (response.status === 403) {
                    return { success: false, msg: 'Forbidden!' }
                }
                return { success: false, msg: 'Upload failed!' }
            }
        })
        .then((data) => {
            var notyf = new Notyf()
            if (data.success) {
                notyf.success({
                    message: data.msg,
                    duration: 3000,
                    dismissible: true,
                    position: { x: 'right', y: 'top' }
                })
            } else {
                notyf.error({
                    message: data.msg,
                    duration: 3000,
                    dismissible: true,
                    position: { x: 'right', y: 'top' }
                })
            }
        })
        .catch((error) => {
            alert(error)
        })
}

document.getElementById('upLoad').onchange = function (e) {
    const files = e.target.files

    for (let i = 0; i < files.length; i++) {
        const file = files[i]
        console.log(`FileName: ${file.name}, Size: ${file.size} bytes, Type: ${file.type}`)
        uploadFile(file)
    }
}
