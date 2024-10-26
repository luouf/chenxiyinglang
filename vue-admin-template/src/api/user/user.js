import request from '@/utils/request'

// 定义公共的api 
const Path = '/admin/system/sysUser';

export default {
    // 查询用户和分页
    getUserPageInfo(page, limit, searchObj) {
        return request({
            url: `${Path}/${page}/${limit}`,
            method: 'get',
            params: searchObj
        })
    },
    //根据id修改状态
    updateStatus(id, status) {
        return request({
            url: `${Path}/${id}/${status}`,
            method: 'post'
        })
    },
    //增加用户
    addUser(user){
        return request({
            url: `${Path}/addUser`,
            method:'post',
            data: user
        })
    },
    //修改用户
    editUserById(user){
        return request({
            url: `${Path}/editUserById`,
            method: 'post',
            data: user
        })
    },
    //删除用户
    deleteById(id){
        return request({
            url : `${Path}/deleteById/${id}`,
            method: 'delete',
        })
    },
    //批量删除用户
    //批量删除
    batchRemove(ids){
        return request({
          url : `${Path}/batchRemove`,
          method :'delete',
          data:ids
        })
      }
}