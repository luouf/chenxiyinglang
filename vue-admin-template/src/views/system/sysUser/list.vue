<template>
  <div class="app-container">
    <el-button type="primary" @click="toShow()">添加</el-button>
    <el-button type="danger" @click="deleteSome()">批量删除</el-button>
    <div class="block" style="margin-left: 50px; display: inline-block;">
    <el-date-picker
      v-model="createTimes"
      type="datetimerange"
      range-separator="至"
      start-placeholder="开始日期"
      end-placeholder="结束日期"
      value-format="yyyy-MM-dd HH:mm:ss"
      @input="test()">
    </el-date-picker>
  </div>
    <div style="display: inline-block; width: 20%;">
      <el-input style="  margin:0 50%;  width: 200px;" v-model="searchObj.keyword" placeholder="用户名/姓名/手机"
        @input="toSearch" />
    </div>
    <!-- 列表 -->
    <el-table v-loading="listLoading" :data="list" stripe border style="width: 100%;margin-top: 10px;"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50px">
      </el-table-column>
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="username" label="用户名" width="180" />
      <el-table-column prop="name" label="姓名" width="110" />
      <el-table-column prop="phone" label="手机" />
      <el-table-column label="状态" width="80">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" :active-value="1" :inactive-value="0" @input="switchStatus(scope.row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" />

      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">

          <el-button type="primary" icon="el-icon-edit" size="mini" @click="toShow(scope.row)" title="修改" circle />
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row.id)" title="删除"
            circle />
          <el-button type="warning" icon="el-icon-s-tools" size="mini" @click="showAssignRole(scope.row)" title="分配角色"
            circle></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <div class="block">
      <el-pagination style=" width: 320px;margin: 0 auto;" @current-change="fetchData" :current-page="page"
        :page-size="limit" layout="total,  prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>
    <el-dialog :title="title" :visible.sync="dialogVisible">
      <el-form :model=sysUser>
        <el-form-item label="用户名" label-width="120px">
          <el-input v-model="sysUser.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" label-width="120px">
          <el-input v-model="sysUser.password"></el-input>
        </el-form-item>
        <el-form-item label="姓名" label-width="120px">
          <el-input v-model="sysUser.name"></el-input>
        </el-form-item>
        <el-form-item label="手机" label-width="120px">
          <el-input v-model="sysUser.phone"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel()">取 消</el-button>
        <el-button type="primary" @click="confirm()">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 分配角色对话框 -->
    <el-dialog title="分配角色" :visible.sync="dialogRoleVisible">
      <el-form label-width="80px">
        <el-form-item label="用户名">
          <el-input disabled :value="sysUser.username"></el-input>
        </el-form-item>

        <el-form-item label="角色列表">
          <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
          <div style="margin: 15px 0;"></div>
          <el-checkbox-group v-model="userRoleIds" @change="handleCheckedChange">
            <el-checkbox v-for="role in allRoles" :key="role.id" :label="role.id">{{role.roleName}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="assignRole" size="small">保存</el-button>
        <el-button @click="dialogRoleVisible = false" size="small">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import api from '@/api/user/user.js'
import roleApi from '@/api/role/role.js'
export default {
  data() {
    return {
      listLoading: true, // 数据是否正在加载
      list: [], // 数据列表
      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 3, // 每页记录数
      searchObj: {}, // 查询表单对象
      // 处理时间范围条件的
      createTimes: [],
      dialogVisible: false,
      sysUser: {},
      title: "",
      saveBtnDisabled: false,
      multipleSelection: [],
      dialogRoleVisible: false,
      allRoles: [], // 所有角色列表
      userRoleIds: [], // 用户的角色ID的列表
      isIndeterminate: false, // 是否是不确定的
      checkAll: false // 是否全选
    }
  },
  created() {
    this.fetchData();
  },
  methods: {
    test(){
      if(this.createTimes!=null){
        this.searchObj.createTimeBegin=this.createTimes[0];
        this.searchObj.createTimeEnd=this.createTimes[1];
        console.log(this.createTimes);
        
      }else
        this.searchObj={}
      this.fetchData();
    },
    // 修改状态
    switchStatus(row) {
      // 如果 可用  设置为 不可用  如果不可用  设置为 可用
      api.updateStatus(row.id, row.status).then(response => {
        //a.给出提示
        this.$message.success(response.message || '操作成功');
      })
      
    },
    toSearch() {
      this.fetchData();
    },
    toShow(row) {
      this.dialogVisible = true;
      this.sysUser={};
      if (row != null) {
        this.title = "修改用户"
        this.sysUser = row;
      } else
        this.title = "添加用户"
    },
    confirm() {
      if (this.sysUser.id != null && this.sysUser.id != '')
        this.edit();
      else
        this.add();
      this.dialogVisible = false;
      this.sysUser = {};
      this.fetchData();
    },
    edit() {
      api.editUserById(this.sysUser).then(response => {
        this.$message({
          message: '修改成功！',
          type: 'success'
        });
      })
    },
    add() {
      api.addUser(this.sysUser).then(response => {
        this.$message({
          message: '修改成功！',
          type: 'success'
        });
      })
    },
    cancel() {
      this.dialogVisible = false;
      this.$message('已执行取消操作。。');
    },
    fetchData(page = 1) {
      this.page = page;
      if (this.createTimes && this.createTimes.length == 2) {
        this.searchObj.createTimeBegin = this.createTimes[0];
        this.searchObj.createTimeEnd = this.createTimes[1];
      }

      api.getUserPageInfo(this.page, this.limit, this.searchObj).then(
        response => {
          //this.list = response.data.list
          this.list = response.data.records;
          this.total = response.data.total;
          // 数据加载并绑定成功
          this.listLoading = false;
        }
      )
    },
    resetData() {
      // 1.清空条件搜索
      this.searchObj = {};
      //2.清空日期条件
      this.createTimes = [];
      //3.刷新数据列表
      this.fetchData();
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection);
    },
    removeDataById(id) {
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        api.deleteById(id).then(response => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.fetchData();
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    deleteSome() {
      this.$confirm('此操作将永久删除这些用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //先获取id集合
        const ids = [];
        for (let i = 0; i < this.multipleSelection.length; i++) {
          ids.push(this.multipleSelection[i].id);
        }
        api.batchRemove(ids).then(response => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.fetchData();
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    assignment(id) {

    },
    //添加以下方法

    //展示分配角色
    showAssignRole(row) {
      this.sysUser = row
      this.dialogRoleVisible = true
      roleApi.getRolesByUserId(row.id).then(response => {
        console.log(response.data);
        this.allRoles = response.data.allRoles
        this.userRoleIds = response.data.userRoleIds
        this.checkAll = this.userRoleIds.length === this.allRoles.length
        this.isIndeterminate = this.userRoleIds.length > 0 && this.userRoleIds.length < this.allRoles.length
      })
    },

    /*
    全选勾选状态发生改变的监听
    */
    handleCheckAllChange(value) {// value 当前勾选状态true/false
      // 如果当前全选, userRoleIds就是所有角色id的数组, 否则是空数组
      this.userRoleIds = value ? this.allRoles.map(item => item.id) : []
      // 如果当前不是全选也不全不选时, 指定为false
      this.isIndeterminate = false
    },

    /*
    角色列表选中项发生改变的监听
    */
    handleCheckedChange(value) {
      const { userRoleIds, allRoles } = this
      this.checkAll = userRoleIds.length === allRoles.length && allRoles.length > 0
      this.isIndeterminate = userRoleIds.length > 0 && userRoleIds.length < allRoles.length
    },

    //分配角色
    assignRole() {
      let assginRoleVo = {
        userId: this.sysUser.id,
        roleIdList: this.userRoleIds
      }
      roleApi.assignRoles(assginRoleVo).then(response => {
        this.$message.success(response.message || '分配角色成功')
        this.dialogRoleVisible = false
        this.fetchData(this.page)
      })
    },
  }
}
</script>