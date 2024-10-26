<template>
    <div class="app-container">
        <el-button type="primary" @click="toShow()" >添加</el-button>
        <el-button type="danger" @click="deleteSome()">批量删除</el-button>
        <div style="display: inline-block; width: 70%;">
            <el-input style="  margin:0 50%;  width: 200px;" v-model="searchObj.roleName" placeholder="输入角色名搜索"
                @input="toSearch" />
        </div>
        <el-table :data=list style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="50px">
            </el-table-column>
            <el-table-column label="id" prop="id" width="50px">
            </el-table-column>
            <el-table-column label="角色名" prop="roleName">
            </el-table-column>
            <el-table-column label="角色代号" prop="roleCode">
            </el-table-column>
            <el-table-column label="描述" prop="description">
            </el-table-column>

            <el-table-column label="操作" text-align="right">

                <template slot-scope="scope">
                    <el-button size="mini" type="primary" icon="el-icon-edit" circle @click="toShow(scope.row)"
                        title="修改"></el-button>
                    <el-button size="mini" type="danger" icon="el-icon-delete" circle @click="toDelete(scope.row)"
                        title="删除"></el-button>
                    <el-button type="warning" icon="el-icon-s-tools" size="mini" circle @click="showAssignAuth(scope.row)"
                        title="分配权限" />
                </template>
            </el-table-column>
        </el-table>
        <div class="block">
            <el-pagination style=" width: 320px;margin: 0 auto;" @current-change="fetchData" :current-page="page"
                :page-size="limit" layout="total,  prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>
        <el-dialog :title="title" :visible.sync="dialogFormVisible">
            <el-form v-model="form">
                <el-form-item label="角色名称" label-width="120px">
                    <el-input v-model="form.roleName"></el-input>
                </el-form-item>
                <el-form-item label="角色代号" label-width="120px">
                    <el-input v-model="form.roleCode"></el-input>
                </el-form-item>
                <el-form-item label="角色描述" label-width="120px">
                    <el-input v-model="form.description"></el-input>
                </el-form-item>
                <el-form-item label="id" label-width="120px" hidden>
                    <el-input v-model="form.id"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancel()">取 消</el-button>
                <el-button type="primary" @click="confirm()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import api from '@/api/role/role.js'

export default {
    data() {
        return {
            list: [],
            total: 0,
            page: 1,
            limit: 5,
            searchObj: {},
            title: "",
            dialogFormVisible: false,
            form: {},
            multipleSelection: []
        }
    },
    created() {
        this.fetchData(1);
    },
    methods: {
        fetchData(page = 1) {
            this.page = page;
            api.getPageList(this.page, this.limit, this.searchObj).then(response => {
                this.list = response.data.records;
                this.total = response.data.total;
            })
        },
        showAssignAuth(row) {
            this.$router.push('/system/assignAuth?id=' + row.id + '&roleName=' + row.roleName);
        },
        toShow(row) {
            this.form = {};
            this.dialogFormVisible = true;
            this.title = "添加角色";
            if (row != null) {
                this.title = "编辑角色";
                this.form = row;
            }
        },
        confirm() {
            if (this.form.id != null && this.form.id != '')
                this.editRole();
            else
                this.addRole();
            this.dialogFormVisible = false;
            this.form = {};
        },
        editRole() {
            api.updateRole(this.form).then(response => {
                this.$message({
                    message: '修改成功！',
                    type: 'success'
                }); 
            });
            this.fetchData();
        },
        addRole() {
            api.addRole(this.form).then(response => {
                this.$message({
                    message: '添加成功！',
                    type: 'success'
                });
                this.fetchData();
            })
        },
        cancel() {
            this.dialogFormVisible = false;
            this.$message('已执行取消操作。。');
        },
        toSearch() {
            this.fetchData();
        },
        toDelete(row) {
            this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                api.deleteById(row.id).then(response => {
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
        handleSelectionChange(val) {
            this.multipleSelection = val;
            console.log(this.multipleSelection);
        },
        deleteSome() {
            //获取id集合
            const ids = [];
            for (let i = 0; i < this.multipleSelection.length; i++) {
                ids.push(this.multipleSelection[i].id);
            }
            console.log(ids);
            this.$confirm('此操作将永久删除这些角色, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
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
        }

    }
}
</script>

<style></style>