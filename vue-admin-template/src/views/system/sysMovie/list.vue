<template>
  <div v-loading="loading" element-loading-text="文件上传中......">
    <!--查询表单-->
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="24">
            <el-form-item label="影视名称">
              <el-input style="width: 45%" v-model="searchObj.name" placeholder="影视名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="display: flex">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="fetchPageList()">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetData">重置</el-button>
        </el-row>
      </el-form>
    </div>

    <!-- 工具条 -->
    <div class="tools-div">
      <el-button type="success" icon="el-icon-plus" size="mini" @click="add">添 加</el-button>
    </div>

    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%">
      <el-form ref="dataForm" :model="sysMovie" label-width="150px" size="small" style="padding-right: 40px">
        <el-form-item label="影视名称">
          <el-input v-model="sysMovie.name" />
        </el-form-item>

        <el-form-item label="所属栏目" prop="cid">
          <el-select v-model="sysMovie.cid" placeholder="请输入影视分类">
            <el-option v-for="item in categoryList" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="关键字" prop="keyword">
          <el-input v-model="sysMovie.keyword"></el-input>
        </el-form-item>

        <el-form-item label="导演" prop="director">
          <el-input v-model="sysMovie.director"></el-input>
        </el-form-item>

        <el-form-item label="演员" prop="actor">
          <el-input v-model="sysMovie.actor"></el-input>
        </el-form-item>

        <el-form-item label="影视图片" prop="image">
          <input type="file" @change="handleImageFileChange" />
          <!-- <input type="file"  /> -->
          <img v-if="imageUrl" :src="imageUrl" alt="Avatar Preview" width="100" />
        </el-form-item>

        <el-form-item label="影视文件" prop="video">
          <input type="file" @change="handleMoviceFileChange" />
        </el-form-item>

        <el-form-item label="影视描述" prop="description">
          <el-input v-model="sysMovie.description"></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 表格 -->
    <el-table v-loading="listLoading" :data="list" stripe border style="width: 100%; margin-top: 10px">
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="影视名称" />
      <el-table-column prop="cid" label="影视类型" :formatter="cidformatter" />
      <el-table-column prop="director" label="导演" width="160" />
      <el-table-column prop="image" label="剧照">
        <template slot-scope="scope">
          <viewer>
            <img :src="scope.row.image" alt="" style="height: 80px" />
          </viewer>
        </template>

      </el-table-column>


      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)" title="修改" />
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row.id)" title="删除" />
          <el-button type="warning" icon="el-icon-baseball" size="mini" @click="showPlayPage(scope.row)" title="播放视频" />
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination :current-page="page" :total="total" :page-size="limit" style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper" @current-change="fetchPageList" />


  </div>
</template>

<script>
// 导入
import api from "@/api/movie/movie.js";

// 导入分类
import categoryapi from "@/api/category/category.js";

export default {
  data() {
    return {
      list: [],
      page: 1,
      limit: 5,
      total: 0,
      searchObj: {},
      listLoading: false,
      dialogVisible: false,
      sysMovie: {},
      // 影视分类列表
      categoryList: [],
      fileurl: "",
      loading: false,
      VideodialogVisible: false,
      imageUrl: "",
      imageFile: "",
      moviceFile: ""
    };
  },
  mounted() {

  },
  created() {
    //this.listLoading=true;
    this.fetchPageList();
    this.findAll();
  },
  methods: {
    handleMoviceFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        this.moviceFile = file;
      }
    },
    handleImageFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        this.imageFile = file;
        this.imageUrl = URL.createObjectURL(file); // 生成预览URL  
        console.log("imageUrl:", this.imageUrl);

      }
    },
    cidformatter(row, cloumn) {
      if (row.cid == 2) {
        return '电影'
      }
      else if (row.cid == 1) {
        return '电视剧'
      }
    },
    showPlayPage(row) {
      this.$router.push('/system/assignVideo?id=' + row.id);
    },
    findAll() {
      categoryapi.findAll().then((response) => {
        this.categoryList = response.data;
      });
    },
    edit(id) {
      //1.弹框
      this.dialogVisible = true;
      //2.赋值
      api.getMovieById(id).then((response) => {
        this.sysMovie = response.data;
      });
    },

    saveOrUpdate() {
      if (this.sysMovie.id != null) {
        this.updateMovie();
      } else {
        this.addMovie();
      }
    },
    async addMovie() {
      try {
        const imageFile = this.imageFile;
        const moviceFile = this.moviceFile;
        this.imageUrl = "";
        this.imageFile = "";
        this.moviceFile = "";
        const response = await api.addMovie(this.sysMovie);
        this.sysMovie = {};
        console.log("response.data:", response.data);
        let AddMovice = response.data;

        //关闭弹框
        this.dialogVisible = false;
        api
          .getMoviePageInfo(1, this.limit, this.searchObj)
          .then((response) => {
            // this.listLoading=false;
            // console.log(response);
            this.list = response.data.records;
            this.total = response.data.total;
          });
        // 获取 image 和 playId  
        if (imageFile != null && imageFile != "") {
          console.log("imageFile:", imageFile);
          const formData = new FormData();
          formData.append('uploadImage', imageFile); // 'uploadImage' 是后端期望的字段名  

          // 上传图像  
          const imageResponse = await fetch('/dev-api/admin/system/upload/uploadImage', {
            method: 'POST',
            body: formData,
          });

          if (imageResponse.ok) {
            const imageData = await imageResponse.json();
            console.log("imageResponse:", imageData);
            AddMovice.image = imageData.data;  // assume imageData contains the image URL or ID  
            await api.updateMovie(AddMovice).then(response => {
              this.$message({
                type: "success",
                message: "《" + AddMovice.name + "》剧照上传成功!",
              });
            });

          } else {
            console.error('图像上传失败:', imageResponse.statusText);
            return; // 处理上传错误情况  
          }
        }
        //获取视频文件
        if (moviceFile != null && moviceFile != "") {
          console.log("moviceFile:", moviceFile);
          const formData = new FormData();
          formData.append('uploadVideo', moviceFile);

          const moviceResponse = await fetch('/dev-api/admin/system/upload/uploadVideo', {
            method: 'POST',
            body: formData,
          });

          if (moviceResponse.ok) {
            const moviceData = await moviceResponse.json();
            console.log("moviceResponse:", moviceData);
            AddMovice.playId = moviceData.data;
            await api.updateMovie(AddMovice).then(response => {
              this.$message({
                type: "success",
                message: "《" + AddMovice.name + "》视频上传成功!",
              });
            });

          } else {
            console.error('视频上传失败:', moviceResponse.statusText);
            return; // 处理上传错误情况  
          }
        }

        console.log("AddMovice:", AddMovice);

        // 更新电影信息  
        await api.updateMovie(AddMovice).then(response=>{
          this.$message({
          type: "success",
          message: "添加成功!",
        });
        });

        
        // 刷新列表  
        this.fetchPageList(1);
      } catch (error) {
        console.error('错误:', error);
      }

    },
    async updateMovie() {
      this.dialogVisible = false;
      const imageFile = this.imageFile;
      const moviceFile = this.moviceFile;
      const UpdateMovice = { ...this.sysMovie };
      this.imageUrl = "";
      this.imageFile = "";
      this.moviceFile = "";
      this.sysMovie = {};
      // 获取 image 和 playId  
      if (imageFile != '' && imageFile != null) {
        console.log("imageFile:", imageFile);
        const formData = new FormData();
        formData.append('uploadImage', imageFile); // 'uploadImage' 是后端期望的字段名  

        // 上传图像  
        const imageResponse = await fetch('/dev-api/admin/system/upload/uploadImage', {
          method: 'POST',
          body: formData,
        });

        if (imageResponse.ok) {
          const imageData = await imageResponse.json();
          console.log("imageResponse:", imageData);
          UpdateMovice.image = imageData.data;  // assume imageData contains the image URL or ID 
          api.updateMovie(UpdateMovice).then((response) => {
            this.$message({
              type: "success",
              message: "《" + UpdateMovice.name + "》剧照上传成功!",
            });
          })
        } else {
          console.error('图像上传失败:', imageResponse.statusText);
          return; // 处理上传错误情况  
        }
      }
      //获取视频文件
      if (moviceFile != '' && moviceFile != null) {
        console.log("moviceFile:", moviceFile);
        const formData = new FormData();
        formData.append('uploadVideo', moviceFile);

        const moviceResponse = await fetch('/dev-api/admin/system/upload/uploadVideo', {
          method: 'POST',
          body: formData,
        });

        if (moviceResponse.ok) {
          const moviceData = await moviceResponse.json();
          console.log("moviceResponse:", moviceData);
          UpdateMovice.playId = moviceData.data;
          api.updateMovie(UpdateMovice).then(response => {
            this.$message({
              type: "success",
              message: "《" + UpdateMovice.name + "》视频上传成功!",
            });
          })
        } else {
          console.error('视频上传失败:', moviceResponse.statusText);
          return; // 处理上传错误情况  
        }
      }
      api.updateMovie(UpdateMovice).then((response) => {
        this.$message({
          type: "success",
          message: "修改成功!",
        });
      });
      this.fetchPageList(1);
    },
    add() {
      this.sysMovie = {};
      this.dialogVisible = true;
    },
    removeDataById(id) {
      this.$confirm("此操作将永久删除该影视, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        api.removeMovieById(id).then((response) => {
          this.$message({
            type: "success",
            message: "删除成功!",
          });
          this.fetchPageList(1);
        });
      });
    },
    resetData() {
      //1.清空搜索条件
      this.searchObj = {};
      //2.再次刷新列表
      this.fetchPageList();
    },
    fetchPageList(page = 1) {
      this.page = page;
      api
        .getMoviePageInfo(this.page, this.limit, this.searchObj)
        .then((response) => {
          // this.listLoading=false;
          // console.log(response);
          this.list = response.data.records;
          this.total = response.data.total;
        });
    },
  },
};
</script>

<style lang="less" scoped></style>