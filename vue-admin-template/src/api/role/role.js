import request from '@/utils/request'
const Path="/admin/system/sysRole"
export default {
    // 列表 
    getPageList(page,limit,searchObj){
      return request({
           url: `${Path}/${page}/${limit}`,
          method: 'get',
          params: searchObj
      })
    },
    // 编辑用户
    updateRole(Role){
      return request({
        url: `${Path}/updateRole`,
        method:'post',
        data:Role
      })
    },
    // 新增用户
    addRole(Role){
      return request({
        url: `${Path}/addRole`,
        method:'post',
        data:Role
      })
    },
    //删除
    deleteById(id){
      return request({
        url : `${Path}/removeRole/${id}`,
        method :'delete'
      })
    },
    //批量删除
    batchRemove(ids){
      return request({
        url : `${Path}/batchRemove`,
        method :'delete',
        data:ids
      })
    },
    getRolesByUserId(userId){
      return request({
        url: `${Path}/toAssign/${userId}`,
        method:'get'
      })
    },
    assignRoles(assignRoleVo){
      return request({
        url: `${Path}/doAssign`,
        method:'post',
        data:assignRoleVo
      })
    }
}
