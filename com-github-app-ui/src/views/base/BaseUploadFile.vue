<template>
  <el-dialog title="上传文件" :visible.sync="isShowUploadFileDialog" width="60%" center>
    <div>
      <el-table :data="files" border fit highlight-current-row max-height="300" style="width: 100%">
        <el-table-column label="快照" width="150" align="center">
          <template slot-scope="scope">
            <img v-if="scope.row.thumb" :src="scope.row.thumb" width="40" height="auto" />
            <span v-else>No Image</span>
          </template>
        </el-table-column>
        <el-table-column label="名称" width="160" align="center">
          <template slot-scope="scope">
            {{scope.row.name}}
          </template>
        </el-table-column>
        <el-table-column label="大小" width="120" align="center">
          <template slot-scope="scope">
            {{formatFileSize(scope.row.size/1024)}} Kb
          </template>
        </el-table-column>
        <el-table-column label="上传速度" align="center">
          <template slot-scope="scope">
            <el-progress :percentage="scope.row.progress"></el-progress> {{scope.row.speed}} Kb/s
          </template>
        </el-table-column>
        <el-table-column label="上传结果" width="120" align="center">
          <template slot-scope="scope">
            <i v-if="scope.row.success" class="el-icon-success"></i>
            <i v-if="scope.row.error" class="el-icon-error"></i>{{scope.row.error}}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="80" align="center">
          <template slot-scope="scope">
            <el-button @click.prevent="$refs.upload.remove(scope.row)" type="text" size="mini">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <file-upload class="btn btn-primary"
                   :post-action="uploadUrl"
                   :extensions="extensions"
                   :multiple="true"
                   :size="1024 * 1024 * 100"
                   :headers="headers"
                   :drop="true"
                   :drop-directory="true"
                   v-model="files"
                   @input-filter="inputFilter"
                   @input-file="inputFile"
                   ref="upload">
        <i class="fa fa-plus"></i>
        <el-button type="primary" @click="onAddFile" size="mini">文件</el-button>
        <el-button type="primary" @click="onAddFolder" size="mini">文件夹</el-button>
        <el-button type="success" v-if="!$refs.upload || !$refs.upload.active" @click.prevent="$refs.upload.active = true" size="mini">开始上传</el-button>
        <el-button type="warning" v-else @click.prevent="$refs.upload.active = false" size="mini">停止上传</el-button>
      </file-upload>
    </div>
  </el-dialog>
</template>

<script>
  import { getToken } from '@/utils/auth'
  import FileUpload from 'vue-upload-component'

  export default {
    name: 'BaseUploadFile',
    components: {
      FileUpload
    },
    data() {
      return {
        headers: {},
        isShowUploadFileDialog: false,
        files: []
      }
    },
    watch: {
      isShowUploadFileDialog() {
        this.$emit('onClose')
      }
    },
    props: {
      uploadUrl: {
        type: String
      },
      extensions: {
        type: String
      }
    },
    methods: {
      formatFileSize(size) {
        return Math.round(size * 100) / 100
      },
      inputFilter(newFile, oldFile, prevent) {
        if (newFile && !oldFile) {
          // Before adding a file
          // 添加文件前
          // Filter system files or hide files
          // 过滤系统文件 和隐藏文件
          if (/(\/|^)(Thumbs\.db|desktop\.ini|\..+)$/.test(newFile.name)) {
            return prevent()
          }
          // Filter php html js file
          // 过滤 php html js 文件
          if (/\.(php5?|html?|jsx?)$/i.test(newFile.name)) {
            return prevent()
          }
        }
      },
      inputFile(newFile, oldFile) {
        if (newFile && !oldFile) {
          // add
          console.log('add', newFile)
        }
        if (newFile && oldFile) {
          // update
          console.log('update', newFile)
        }
        if (!newFile && oldFile) {
          // remove
          console.log('remove', oldFile)
        }
        if (newFile && (!oldFile || newFile.file !== oldFile.file)) {
          // Create a blob field
          // 创建 blob 字段
          newFile.blob = ''
          var URL = window.URL || window.webkitURL
          if (URL && URL.createObjectURL) {
            newFile.blob = URL.createObjectURL(newFile.file)
          }
          // Thumbnails
          // 缩略图
          newFile.thumb = ''
          if (newFile.blob && newFile.type.substr(0, 6) === 'image/') {
            newFile.thumb = newFile.blob
          }
        }
      },
      onAddFile() {
        if (!this.$refs.upload.features.directory) {
          this.alert('Your browser does not support')
          return
        }
        var input = this.$refs.upload.$el.querySelector('input')
        input.onclick = null
        input.click()
      },
      onAddFolder() {
        if (!this.$refs.upload.features.directory) {
          this.alert('Your browser does not support')
          return
        }
        var input = this.$refs.upload.$el.querySelector('input')
        input.directory = true
        input.webkitdirectory = true
        this.directory = true
        input.onclick = null
        input.click()
        input.onclick = (e) => {
          this.directory = false
          input.directory = false
          input.webkitdirectory = false
        }
      },

      beginUpload() {
        this.headers['X-Token'] = getToken()
        this.isShowUploadFileDialog = true
        this.files = []
      }
    }
  }
</script>
