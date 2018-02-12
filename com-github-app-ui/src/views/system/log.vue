<template>
  <div class="app-container">
    <el-form inline>
      <el-form-item label="帐号">
        <el-input placeholder="帐号"/>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="searchTime"
          type="datetimerange"
          :picker-options="pickerOptions"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          align="right">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="Loading" border fit highlight-current-row  max-height="760" style="width: 100%">
      <el-table-column label="时间" width="180" align="center">
        <template slot-scope="scope">
          {{formattimeStr(scope.row.logId)}}
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
      <el-table-column label="操作记录" align="center">
        <template slot-scope="scope">
          {{scope.row.description.substring(0, 130)}}
        </template>
      </el-table-column>
    </el-table>
    <base-pagination :total="total" v-on:change="search" ref="pageinfo"></base-pagination>
  </div>
</template>

<script>
  import { getList } from '@/api/log'
  import { formatTimeUs } from '@/utils/index'
  import BasePagination from '../base/BasePagination'

  export default {
    components: { BasePagination },
    data() {
      return {
        list: null,
        listLoading: true,
        total: 0,
        searchTime: '',
        pickerOptions: {
          shortcuts: [{
            text: '最近一小时',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '最近一天',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '最近一周',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', [start, end])
            }
          }]
        }
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
          this.total = response.data.total
          console.log(this.total)
          this.listLoading = false
        })
      },
      formattimeStr(cellValue) {
        return formatTimeUs(cellValue)
      },
      search() {
        console.log(this.searchTime[0].getTime)
        console.log(this.$refs.pageinfo.getPageParam)
      }
    }
  }
</script>
