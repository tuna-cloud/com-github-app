<template>
  <div class="app-container">

    <el-row type="flex"  justify="end">
      <el-col :span="1">
        <el-button type="success" @click="addNew" size="mini">添加</el-button>
      </el-col>
    </el-row>

    <el-table :data="list" v-loading.body="listLoading" element-loading-text="Loading" border fit highlight-current-row
              max-height="760" style="width: 100%">
      <el-table-column label="角色编码" align="center">
        <template slot-scope="scope">
          {{scope.row.roleId}}
        </template>
      </el-table-column>
      <el-table-column label="角色名称" align="center">
        <template slot-scope="scope">
          {{scope.row.name}}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button @click="power(scope.row)" type="text" size="mini">分配权限</el-button>
          <el-button @click="edit(scope.row)" type="text" size="mini">编辑</el-button>
          <el-button @click="del(scope.row)" type="text" size="mini">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <base-pagination :total="total" v-on:change="fetchData" ref="pageinfo"></base-pagination>

    <el-dialog title="角色信息" :visible.sync="editDialogVisible" width="30%" center>
      <el-form :model="role" :rules="rules" ref="editForm">
        <el-form-item label="角色名称" label-width="120px" prop="name">
          <el-input v-model="role.name" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelEdit">取 消</el-button>
        <el-button type="primary" @click="saveForm">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="权限分配" :visible.sync="powerDialogVisible" width="35%" center>
      <el-transfer
        v-model="rolePopedoms"
        filterable
        :render-content="renderFunc"
        :titles="['系统权限', '角色权限']"
        :button-texts="['到左边', '到右边']"
        :format="{
          noChecked: '${total}',
          hasChecked: '${checked}/${total}'
         }"
        :data="popedoms">
        <el-button type="primary" class="transfer-footer" @click="savePower" slot="right-footer" size="small">保存</el-button>
      </el-transfer>
    </el-dialog>
  </div>
</template>

<script>
  import { deleteRoleById } from '@/api/role'
  import { saveOrUpdate } from '@/api/role'
  import { getAllRoles } from '@/api/role'
  import { getRolePopedomsByRoleId, saveRolePopedoms } from '@/api/role'
  import BasePagination from '../base/BasePagination'
  import { Message, MessageBox } from 'element-ui'

  export default {
    components: { BasePagination },
    data() {
      return {
        list: [],
        listLoading: true,
        total: 0,
        editDialogVisible: false,
        powerDialogVisible: false,
        popedoms: [],
        rolePopedoms: [1],
        roleId: -1,
        renderFunc(h, option) {
          return <span>{ option.label }</span>
        },
        role: {},
        rules: {
          name: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
            { min: 2, max: 12, message: '长度在 2 到 12 个字符', trigger: 'blur' }
          ]
        }
      }
    },
    mounted() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.listLoading = true
        var params = this.$refs.pageinfo.getPageParam
        getAllRoles(params).then(response => {
          this.list = response.data.list
          this.total = response.data.total
          this.listLoading = false
        })
      },
      edit(row) {
        this.role = row
        this.editDialogVisible = true
      },
      saveForm() {
        this.$refs['editForm'].validate((valid) => {
          if (valid) {
            saveOrUpdate(this.role).then(response => {
              this.editDialogVisible = false
              this.fetchData()
            })
          } else {
            return false
          }
        })
      },
      cancelEdit() {
        this.editDialogVisible = false
        this.$refs['editForm'].resetFields()
      },
      power(row) {
        this.roleId = row.roleId
        getRolePopedomsByRoleId(row.roleId).then(response => {
          var i = 0
          this.popedoms = []
          this.rolePopedoms = []
          for (i = 0; i < response.data.popedoms.length; i++) {
            var obj = {}
            obj.key = response.data.popedoms[i].popedomId
            obj.label = response.data.popedoms[i].name
            obj.remark = response.data.popedoms[i].remark
            obj.disabled = false
            this.popedoms.push(obj)
          }
          for (i = 0; i < response.data.rolePopedoms.length; i++) {
            this.rolePopedoms.push(response.data.rolePopedoms[i].popedomId)
          }
          this.powerDialogVisible = true
        })
      },
      savePower() {
        var rp = []
        var i = 0
        for (i = 0; i < this.rolePopedoms.length; i++) {
          var obj = {}
          obj.roleId = this.roleId
          obj.popedomId = this.rolePopedoms[i]
          rp.push(obj)
        }
        if (this.rolePopedoms.length === 0) {
          var obj1 = {}
          obj1.roleId = this.roleId
          obj1.popedomId = -1
          rp.push(obj1)
        }
        saveRolePopedoms(rp).then(rep => {
          Message({
            message: '权限保存成功',
            type: 'success',
            duration: 3 * 1000
          })
        })
      },
      addNew() {
        this.role = {}
        this.editDialogVisible = true
      },
      del(row) {
        MessageBox.confirm('确认删除次记录？', '提示', {
          confirmButtonText: '继续',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteRoleById(row.roleId).then(response => {
            Message({
              message: '角色删除成功',
              type: 'success',
              duration: 3 * 1000
            })
            this.fetchData()
          })
        })
      }
    }
  }
</script>
