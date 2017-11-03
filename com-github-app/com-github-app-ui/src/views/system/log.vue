<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="Activity name">
        <icon-svg icon-class="zujian" />
        <icon-svg icon-class="a" />
        <icon-svg icon-class="b" />
        <icon-svg icon-class="bug" />
        <icon-svg icon-class="c" />
        <icon-svg icon-class="email" />
        <icon-svg icon-class="EXCEL" />
        <icon-svg icon-class="from" />
        <icon-svg icon-class="icons" />
        <icon-svg icon-class="mima" />
        <icon-svg icon-class="QQ" />
        <icon-svg icon-class="quanxian" />
        <icon-svg icon-class="shouce" />
        <icon-svg icon-class="tab" />
        <icon-svg icon-class="table" />
        <icon-svg icon-class="theme" />
        <icon-svg icon-class="tubiao" />
        <icon-svg icon-class="tuozhuai" />
        <icon-svg icon-class="weixin" />
        <icon-svg icon-class="wujiaoxing" />
        <icon-svg icon-class="xinrenzhinan" />
        <icon-svg icon-class="yanjing" />
        <icon-svg icon-class="yonghuming" />
        <icon-svg icon-class="zhonghe" />
        <i class="el-icon-arrow-down"></i>
        <i class="el-icon-fa-ban"></i>
      </el-form-item>
      <el-form-item label="Activity zone">
        <el-select v-model="form.region" placeholder="please select your zone">
          <el-option label="Zone one" value="shanghai"></el-option>
          <el-option label="Zone two" value="beijing"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Activity time">
        <el-col :span="11">
          <el-date-picker type="date" placeholder="Pick a date" v-model="form.date1" style="width: 100%;"></el-date-picker>
        </el-col>
        <el-col class="line" :span="2">-</el-col>
        <el-col :span="11">
          <el-time-picker type="fixed-time" placeholder="Pick a time" v-model="form.date2" style="width: 100%;"></el-time-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="Instant delivery">
        <el-switch on-text="" off-text="" v-model="form.delivery"></el-switch>
      </el-form-item>
      <el-form-item label="Activity type">
        <el-checkbox-group v-model="form.type">
          <el-checkbox label="Online activities" name="type"></el-checkbox>
          <el-checkbox label="Promotion activities" name="type"></el-checkbox>
          <el-checkbox label="Offline activities" name="type"></el-checkbox>
          <el-checkbox label="Simple brand exposure" name="type"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="Resources">
        <el-radio-group v-model="form.resource">
          <el-radio label="Sponsor"></el-radio>
          <el-radio label="Venue"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="Activity form">
        <el-input type="textarea" v-model="form.desc"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Create</el-button>
        <el-button>Cancel</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        }
      }
    },
    methods: {
      onSubmit() {
        this.$message('submit!')
      }
    }
  }
</script>
