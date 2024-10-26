import request from '@/utils/request'

// 定义公共的api 
const BASE_URL = '/admin/system/sysOperLog';

export default{
    // 查询用户和分页
    getOperLogPageInfo(page,limit,searchObj){
        return request({
          url:`${BASE_URL}/${page}/${limit}`,
          method: 'get',
          params:searchObj
        })
    },
    deleteById(id){
        return request({
            url:`${BASE_URL}/delete/${id}`,
            method:'delete'
        })
    }
}
