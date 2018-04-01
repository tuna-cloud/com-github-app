<template>
  <div class="app-container">
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="Loading" border fit highlight-current-row  max-height="760" style="width: 100%">
      <el-table-column label="账号" width="180" align="center" >
        <template slot-scope="scope">
          {{scope.row.account}}
        </template>
      </el-table-column>
      <el-table-column label="角色" width="110" align="center">
        <template slot-scope="scope">
          <span>{{scope.row.roleName}}</span>
        </template>
      </el-table-column>
      <el-table-column label="登录时间" align="center">
        <template slot-scope="scope">
          {{formatTime(scope.row.firstActiveTime)}}
        </template>
      </el-table-column>
      <el-table-column label="最后活跃时间" align="center">
        <template slot-scope="scope">
          {{formatTime(scope.row.lastActiveTime)}}
        </template>
      </el-table-column>
      <el-table-column label="访问地址" align="center">
        <template slot-scope="scope">
          {{scope.row.remoteIpAddress}}
        </template>
      </el-table-column>
    </el-table>
    <base-pagination :total="total" v-on:change="fetchData" ref="pageinfo"></base-pagination>
  </div>
</template>

<script>
  import { getSessionList } from '@/api/account'
  import { formatMs2String } from '@/utils/timeutils'
  import BasePagination from '../base/BasePagination'

  export default {
    components: { BasePagination },
    data() {
      return {
        list: null,
        listLoading: true,
        total: 0
      }
    },
    mounted() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.listLoading = true
        var params = this.$refs.pageinfo.getPageParam
        params['account'] = this.account
        getSessionList(params).then(response => {
          this.list = response.data.list
          this.total = response.data.total
          this.listLoading = false
        })
      },
      formatTime(time) {
        return formatMs2String(time)
      }
    }
  }
</script>
