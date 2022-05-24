//https://www.cnblogs.com/xinjie-just/p/13513118.html
//npm install --save html2canvas
import html2canvas from 'html2canvas';

function downloadTransactForm (downloadTransactFormId,vueObj) {
    // const tpl = getLoadedTransactForm(downloadTransactFormId);
    // download(tpl.tpl, `${tpl.title}.html`);
    exportTransactformImg(downloadTransactFormId,vueObj);
}

function download (content, filename,isblob) {
    if (window.navigator.msSaveBlob) {
        if (isblob){
            var blob = content;
        } else {
            var blob = new Blob([content]);
        }
        try {
            window.navigator.msSaveBlob(blob, filename);
        }
        catch (e) {
            console.log(e);
        }
    } else {
        // 创建隐藏的可下载链接
        var eleLink = document.createElement('a');
        const e = document.createEvent('MouseEvents');
        e.initEvent('click', false, false);
        eleLink.download = filename;
        eleLink.style.display = 'none';
        // 字符内容转变成blob地址
        if (isblob){
            var blob = content;
        } else {
            var blob = new Blob([content]);
        }
        eleLink.href = URL.createObjectURL(blob);

        // 触发点击
        document.body.appendChild(eleLink);
        eleLink.dispatchEvent(e);
        // 然后移除
        document.body.removeChild(eleLink);
    }
}


function exportTransactformImg(downloadTransactFormId,vueObj) {
    let _this = vueObj;
    let transactFroms = document.querySelectorAll(downloadTransactFormId);
    let transactFrom = transactFroms.length > 0 ? transactFroms[transactFroms.length - 1] : undefined;

    if (!transactFrom){
        _this.$message.error('办理单下载异常');
        return ;
    }
    html2canvas(transactFrom, {
        scale: 1, //放大一倍，使图像清晰一点
    }).then((canvas) => {
        const exportImgEle = document.querySelector('#exportImg');
        const exportImgLinkEle = document.querySelector('#exportImgLink');
        // exportImgEle.src = canvas.toDataURL('image/png');
        // exportImgLinkEle.href = canvas.toDataURL('image/png');
        // exportImgLinkEle.click();  // 执行 <a> 元素的下载
        //console.log(canvas.toDataURL('image/png'));
        let transactForm = $(transactFrom).clone();
        let title = transactForm.find('.table-pattern__header span').html();
        if (!title) {
            title = transactForm.find('.table-pattern__header').html();
        }
        title= title ? title.trim() : '';
        if (!transactFrom){
            _this.$message.error('办理单下载异常');
            return ;
        }
        download(dataURLToBlob(canvas.toDataURL('image/png')), `${title}.png`,true);
    }).catch((err) => {
        _this.$message.error("请在办理单界面操作");
        console && console.error(err);
    });
}
// DataURL转Blob对象
function dataURLToBlob(dataurl){
    var arr = dataurl.split(',');
    if (!arr[1]){
        throw new Error('请在办理单界面操作');
        return ;
    }

    var mime = arr[0].match(/:(.*?);/)[1];
    var bstr = atob(arr[1]);
    var n = bstr.length;
    var u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    console.log([u8arr]);
    return new Blob([u8arr], {type:mime});
}
export default transactformDownload;
