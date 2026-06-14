// 校园二手物品交易平台 - 工具函数

// 显示消息提示
function showMessage(message, type = 'success') {
    const messageEl = document.createElement('div');
    messageEl.className = `message message-${type}`;
    messageEl.textContent = message;
    document.body.appendChild(messageEl);

    setTimeout(() => {
        messageEl.remove();
    }, 3000);
}

// 显示成功消息
function showSuccess(message) {
    showMessage(message, 'success');
}

// 显示错误消息
function showError(message) {
    showMessage(message, 'error');
}

// 检查登录状态
function checkLogin() {
    const token = localStorage.getItem('token');
    const userInfo = localStorage.getItem('userInfo');

    if (!token || !userInfo) {
        window.location.href = '/login.html';
        return false;
    }
    return true;
}

// 获取用户信息
function getUserInfo() {
    const userInfo = localStorage.getItem('userInfo');
    return userInfo ? JSON.parse(userInfo) : null;
}

// 保存用户信息
function saveUserInfo(userInfo) {
    localStorage.setItem('userInfo', JSON.stringify(userInfo));
}

// 退出登录
function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('userInfo');
    window.location.href = '/login.html';
}

// 格式化日期
function formatDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}`;
}

// 格式化价格
function formatPrice(price) {
    return `¥${parseFloat(price).toFixed(2)}`;
}

// 获取物品状态标签类名
function getStatusClass(status) {
    const statusMap = {
        '待交易': 'tag-success',
        '已下架': 'tag-info',
        '已售出': 'tag-danger'
    };
    return statusMap[status] || 'tag-info';
}

// 获取物品成色标签类名
function getConditionClass(condition) {
    const conditionMap = {
        '全新': 'tag-success',      // 绿色
        '九成新': 'tag-primary',    // 蓝色
        '八成新': 'tag-warning',    // 橙色
        '其他': 'tag-info'          // 灰色
    };
    return conditionMap[condition] || 'tag-info';
}

// 获取交易状态标签类名
function getTradeStatusClass(status) {
    const statusMap = {
        '待确认': 'tag-warning',
        '待完成': 'tag-warning',
        '已完成': 'tag-success',
        '已拒绝': 'tag-danger'
    };
    return statusMap[status] || 'tag-info';
}

// 显示模态框
function showModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.add('show');
    }
}

// 隐藏模态框
function hideModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.remove('show');
    }
}

// 确认对话框（已移除，直接使用 window.confirm）
// function confirm(message, callback) {
//     if (window.confirm(message)) {
//         callback();
//     }
// }

// 防抖函数
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// 生成分页HTML
function generatePagination(currentPage, totalPages, onPageChange) {
    const paginationHtml = [];

    paginationHtml.push(`
        <button ${currentPage === 1 ? 'disabled' : ''} onclick="${onPageChange}(${currentPage - 1})">上一页</button>
    `);

    for (let i = 1; i <= totalPages; i++) {
        paginationHtml.push(`
            <button class="${i === currentPage ? 'active' : ''}" onclick="${onPageChange}(${i})">${i}</button>
        `);
    }

    paginationHtml.push(`
        <button ${currentPage === totalPages ? 'disabled' : ''} onclick="${onPageChange}(${currentPage + 1})">下一页</button>
    `);

    return paginationHtml.join('');
}

// 物品分类选项
const PRODUCT_CATEGORIES = ['书籍教材', '电子数码', '生活用品', '体育器材', '其他'];

// 物品成色选项
const PRODUCT_CONDITIONS = ['全新', '九成新', '八成新', '其他'];

// 生成分类选项HTML
function generateCategoryOptions(selectedValue = '') {
    return PRODUCT_CATEGORIES.map(category =>
        `<option value="${category}" ${category === selectedValue ? 'selected' : ''}>${category}</option>`
    ).join('');
}

// 生成成色选项HTML
function generateConditionOptions(selectedValue = '') {
    return PRODUCT_CONDITIONS.map(condition =>
        `<option value="${condition}" ${condition === selectedValue ? 'selected' : ''}>${condition}</option>`
    ).join('');
}

// 检查是否为管理员
function isAdmin() {
    const userInfo = getUserInfo();
    return userInfo && userInfo.userType === 'admin';
}

// 导出为Excel（拓展功能）
function exportToExcel(data, filename) {
    // 这里使用简单的CSV格式作为Excel导出
    // 实际项目中可以使用 xlsx.js 等库
    let csv = '';

    if (data.length > 0) {
        // 表头
        csv += Object.keys(data[0]).join(',') + '\n';

        // 数据行
        data.forEach(row => {
            csv += Object.values(row).join(',') + '\n';
        });
    }

    // 创建下载链接
    const blob = new Blob(['\ufeff' + csv], { type: 'text/csv;charset=utf-8;' });
    const link = document.createElement('a');
    const url = URL.createObjectURL(blob);
    link.setAttribute('href', url);
    link.setAttribute('download', filename + '.csv');
    link.style.visibility = 'hidden';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}

// 确保所有函数都在全局作用域中可用
window.showMessage = showMessage;
window.showSuccess = showSuccess;
window.showError = showError;
window.checkLogin = checkLogin;
window.getUserInfo = getUserInfo;
window.saveUserInfo = saveUserInfo;
window.logout = logout;
window.formatDate = formatDate;
window.formatPrice = formatPrice;
window.getStatusClass = getStatusClass;
window.getConditionClass = getConditionClass;
window.getTradeStatusClass = getTradeStatusClass;
window.showModal = showModal;
window.hideModal = hideModal;
window.debounce = debounce;
window.generatePagination = generatePagination;
window.generateCategoryOptions = generateCategoryOptions;
window.generateConditionOptions = generateConditionOptions;
window.isAdmin = isAdmin;
window.exportToExcel = exportToExcel;
window.PRODUCT_CATEGORIES = PRODUCT_CATEGORIES;
window.PRODUCT_CONDITIONS = PRODUCT_CONDITIONS;

