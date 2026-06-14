// 校园二手物品交易平台 - API 封装

// API 基础地址（前后端不分离，使用空字符串即可）
const API_BASE_URL = '';

// HTTP 请求封装
const http = {
    // GET 请求
    get: async (url, params = {}) => {
        const queryString = Object.keys(params)
            .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`)
            .join('&');
        const fullUrl = queryString ? `${API_BASE_URL}${url}?${queryString}` : `${API_BASE_URL}${url}`;

        const token = localStorage.getItem('token');
        const headers = {
            'Content-Type': 'application/json'
        };
        if (token) {
            headers['Authorization'] = `Bearer ${token}`;
        }

        try {
            const response = await fetch(fullUrl, {
                method: 'GET',
                headers: headers
            });
            return await response.json();
        } catch (error) {
            console.error('GET request error:', error);
            throw error;
        }
    },

    // POST 请求
    post: async (url, data = {}) => {
        const token = localStorage.getItem('token');
        const headers = {
            'Content-Type': 'application/json'
        };
        if (token) {
            headers['Authorization'] = `Bearer ${token}`;
        }

        try {
            const response = await fetch(`${API_BASE_URL}${url}`, {
                method: 'POST',
                headers: headers,
                body: JSON.stringify(data)
            });
            return await response.json();
        } catch (error) {
            console.error('POST request error:', error);
            throw error;
        }
    },

    // PUT 请求
    put: async (url, data = {}) => {
        const token = localStorage.getItem('token');
        const headers = {
            'Content-Type': 'application/json'
        };
        if (token) {
            headers['Authorization'] = `Bearer ${token}`;
        }

        try {
            const response = await fetch(`${API_BASE_URL}${url}`, {
                method: 'PUT',
                headers: headers,
                body: JSON.stringify(data)
            });
            return await response.json();
        } catch (error) {
            console.error('PUT request error:', error);
            throw error;
        }
    },

    // DELETE 请求
    delete: async (url) => {
        const token = localStorage.getItem('token');
        const headers = {
            'Content-Type': 'application/json'
        };
        if (token) {
            headers['Authorization'] = `Bearer ${token}`;
        }

        try {
            const response = await fetch(`${API_BASE_URL}${url}`, {
                method: 'DELETE',
                headers: headers
            });
            return await response.json();
        } catch (error) {
            console.error('DELETE request error:', error);
            throw error;
        }
    }
};

// API 接口定义
const api = {
    // 用户相关
    user: {
        // 注册
        register: (data) => http.post('/user/register', data),
        // 登录
        login: (data) => http.post('/user/login', data),
        // 获取当前用户信息
        getInfo: () => http.get('/user/info'),
        // 更新用户信息
        update: (data) => http.put('/user/update', data),
        // 修改密码
        updatePassword: (data) => http.put('/user/password', data),
        // 获取所有学生用户（管理员）
        getAllStudents: () => http.get('/user/students'),
        // 重置密码（管理员）
        resetPassword: (data) => http.put('/user/reset-password', data)
    },

    // 物品相关
    product: {
        // 发布物品
        publish: (data) => http.post('/product/publish', data),
        // 更新物品
        update: (data) => http.put('/product/update', data),
        // 下架物品
        offline: (productId) => http.put(`/product/offline/${productId}`),
        // 获取物品详情
        getById: (productId) => http.get(`/product/${productId}`),
        // 获取我的发布
        getMyProducts: () => http.get('/product/my-products'),
        // 搜索物品
        search: (params) => http.get('/product/search', params),
        // 获取所有物品（管理员）
        getAll: () => http.get('/product/all'),
        // 强制下架（管理员）
        forceOffline: (productId) => http.put(`/product/force-offline/${productId}`)
    },

    // 交易相关
    trade: {
        // 创建交易（简单版）
        create: (data) => http.post('/trade/create', data),
        // 发起交易申请（拓展版）
        request: (data) => http.post('/trade/request', data),
        // 确认交易
        confirm: (tradeId) => http.put(`/trade/confirm/${tradeId}`),
        // 拒绝交易
        reject: (tradeId) => http.put(`/trade/reject/${tradeId}`),
        // 完成交易
        complete: (tradeId) => http.put(`/trade/complete/${tradeId}`),
        // 获取我的交易记录
        getMyTrades: () => http.get('/trade/my-trades'),
        // 获取所有交易（管理员）
        getAll: () => http.get('/trade/all'),
        // 搜索交易（管理员）
        search: (params) => http.get('/trade/search', params)
    },

    // 留言相关
    message: {
        // 发送留言
        send: (data) => http.post('/message/send', data),
        // 根据物品获取留言
        getByProduct: (productId) => http.get(`/message/product/${productId}`),
        // 获取收到的留言
        getReceived: () => http.get('/message/received'),
        // 获取发送的留言
        getSent: () => http.get('/message/sent'),
        // 标记为已读
        markAsRead: (messageId) => http.put(`/message/read/${messageId}`),
        // 获取未读数量
        getUnreadCount: () => http.get('/message/unread-count')
    },

    // 统计相关
    statistics: {
        // 获取统计摘要
        getSummary: (params) => http.get('/statistics/summary', params),
        // 获取热门分类
        getHotCategories: (params) => http.get('/statistics/hot-categories', params)
    }
};
