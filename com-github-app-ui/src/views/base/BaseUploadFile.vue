<template>
  <el-dialog title="上传文件" :visible.sync="isShowUploadFileDialog" width="60%" center>
    <div class="example-simple">
      <h1 id="example-title" class="example-title">Simple Example</h1>
      <div class="upload">
        <ul>
          <li v-for="(file, index) in files" :key="file.id">
            <span>{{file.name}}</span> -
            <span>{{file.size | formatSize}}</span> -
            <span v-if="file.error">{{file.error}}</span>
            <span v-else-if="file.success">success</span>
            <span v-else-if="file.active">active</span>
            <span v-else-if="file.active">active</span>
            <span v-else></span>
          </li>
        </ul>
        <div class="example-btn">
          <file-upload
            class="btn btn-primary"
            post-action="/upload/post"
            extensions="gif,jpg,jpeg,png,webp"
            accept="image/png,image/gif,image/jpeg,image/webp"
            :multiple="true"
            :size="1024 * 1024 * 10"
            v-model="files"
            @input-filter="inputFilter"
            @input-file="inputFile"
            ref="upload">
            <i class="fa fa-plus"></i>
            Select files
          </file-upload>
          <button type="button" class="btn btn-success" v-if="!$refs.upload || !$refs.upload.active" @click.prevent="$refs.upload.active = true">
            <i class="fa fa-arrow-up" aria-hidden="true"></i>
            Start Upload
          </button>
          <button type="button" class="btn btn-danger"  v-else @click.prevent="$refs.upload.active = false">
            <i class="fa fa-stop" aria-hidden="true"></i>
            Stop Upload
          </button>
        </div>
      </div>
    </div>
  </el-dialog>
</template>

<script>
  import FileUpload from 'vue-upload-component'
  export default {
    name: 'BaseUploadFile',
    components: {
      FileUpload
    },
    data() {
      return {
        isShowUploadFileDialog: false,
        files: []
      }
    },
    methods: {
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
      },

      beginUpload() {
        this.isShowUploadFileDialog = true
      }
    }
  }
</script>
