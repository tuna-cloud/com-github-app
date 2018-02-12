<template>
  <div class="app-container">
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="Loading" border fit highlight-current-row>
      <el-table-column align="center" label='ID' width="95">
        <template slot-scope="scope">
          {{scope.$index}}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180" align="center" >
        <template slot-scope="scope">
          {{scope.row.code}}
        </template>
      </el-table-column>
      <el-table-column label="帐号" width="110" align="center">
        <template slot-scope="scope">
          <span>{{scope.row.opAccount}}</span>
        </template>
      </el-table-column>
      <el-table-column label="姓名" width="110" align="center">
        <template slot-scope="scope">
          {{scope.row.opName}}
        </template>
      </el-table-column>
      <el-table-column label="角色" width="110" align="center">
        <template slot-scope="scope">
          {{scope.row.roleName}}
        </template>
      </el-table-column>
      <el-table-column label="时间" width="180" align="center">
        <template slot-scope="scope">
          {{formattimeStr(scope.row.logId)}}
        </template>
      </el-table-column>
      <el-table-column label="操作记录" align="center">
        <template slot-scope="scope">
          {{scope.row.description.substring(0, 130)}}
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  import { getList } from '@/api/log'
  import { formatTimeUs } from '@/utils/index'

  export default {
    data() {
      return {
        list: null,
        listLoading: true
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.listLoading = true
        getList().then(response => {
          this.list = response.data.list
          this.listLoading = false
        })
      },
      formattimeStr(cellValue) {
        return formatTimeUs(cellValue)
      }
    }
  }
</script>
