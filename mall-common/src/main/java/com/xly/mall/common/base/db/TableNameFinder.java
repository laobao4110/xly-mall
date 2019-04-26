package com.xly.mall.common.base.db;

import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TableNameFinder implements SelectVisitor, FromItemVisitor, ExpressionVisitor, ItemsListVisitor {
    private List<String> tables;

    public TableNameFinder() {
    }

    public List<String> getTableList(Select select) {
        this.tables = new ArrayList();
        select.getSelectBody().accept(this);
        return this.tables;
    }

    public void visit(PlainSelect plainSelect) {
        plainSelect.getFromItem().accept(this);
        if (plainSelect.getJoins() != null) {
            Iterator joinsIt = plainSelect.getJoins().iterator();

            while(joinsIt.hasNext()) {
                Join join = (Join)joinsIt.next();
                join.getRightItem().accept(this);
            }
        }

        if (plainSelect.getWhere() != null) {
            plainSelect.getWhere().accept(this);
        }

    }

    public void visit(Union union) {
        Iterator iter = union.getPlainSelects().iterator();

        while(iter.hasNext()) {
            PlainSelect plainSelect = (PlainSelect)iter.next();
            this.visit(plainSelect);
        }

    }

    public void visit(Table tableName) {
        String tableWholeName = tableName.getWholeTableName();
        this.tables.add(tableWholeName);
    }

    public void visit(SubSelect subSelect) {
        subSelect.getSelectBody().accept(this);
    }

    public void visit(Addition addition) {
        this.visitBinaryExpression(addition);
    }

    public void visit(AndExpression andExpression) {
        this.visitBinaryExpression(andExpression);
    }

    public void visit(Between between) {
        between.getLeftExpression().accept(this);
        between.getBetweenExpressionStart().accept(this);
        between.getBetweenExpressionEnd().accept(this);
    }

    public void visit(Column tableColumn) {
    }

    public void visit(Division division) {
        this.visitBinaryExpression(division);
    }

    public void visit(DoubleValue doubleValue) {
    }

    public void visit(EqualsTo equalsTo) {
        this.visitBinaryExpression(equalsTo);
    }

    public void visit(Function function) {
    }

    public void visit(GreaterThan greaterThan) {
        this.visitBinaryExpression(greaterThan);
    }

    public void visit(GreaterThanEquals greaterThanEquals) {
        this.visitBinaryExpression(greaterThanEquals);
    }

    public void visit(InExpression inExpression) {
        inExpression.getLeftExpression().accept(this);
        inExpression.getItemsList().accept(this);
    }

    public void visit(InverseExpression inverseExpression) {
        inverseExpression.getExpression().accept(this);
    }

    public void visit(IsNullExpression isNullExpression) {
    }

    public void visit(JdbcParameter jdbcParameter) {
    }

    public void visit(LikeExpression likeExpression) {
        this.visitBinaryExpression(likeExpression);
    }

    public void visit(ExistsExpression existsExpression) {
        existsExpression.getRightExpression().accept(this);
    }

    public void visit(LongValue longValue) {
    }

    public void visit(MinorThan minorThan) {
        this.visitBinaryExpression(minorThan);
    }

    public void visit(MinorThanEquals minorThanEquals) {
        this.visitBinaryExpression(minorThanEquals);
    }

    public void visit(Multiplication multiplication) {
        this.visitBinaryExpression(multiplication);
    }

    public void visit(NotEqualsTo notEqualsTo) {
        this.visitBinaryExpression(notEqualsTo);
    }

    public void visit(NullValue nullValue) {
    }

    public void visit(OrExpression orExpression) {
        this.visitBinaryExpression(orExpression);
    }

    public void visit(Parenthesis parenthesis) {
        parenthesis.getExpression().accept(this);
    }

    public void visit(StringValue stringValue) {
    }

    public void visit(Subtraction subtraction) {
        this.visitBinaryExpression(subtraction);
    }

    public void visitBinaryExpression(BinaryExpression binaryExpression) {
        binaryExpression.getLeftExpression().accept(this);
        binaryExpression.getRightExpression().accept(this);
    }

    public void visit(ExpressionList expressionList) {
        Iterator iter = expressionList.getExpressions().iterator();

        while(iter.hasNext()) {
            Expression expression = (Expression)iter.next();
            expression.accept(this);
        }

    }

    public void visit(DateValue dateValue) {
    }

    public void visit(TimestampValue timestampValue) {
    }

    public void visit(TimeValue timeValue) {
    }

    public void visit(CaseExpression caseExpression) {
    }

    public void visit(WhenClause whenClause) {
    }

    public void visit(AllComparisonExpression allComparisonExpression) {
        allComparisonExpression.GetSubSelect().getSelectBody().accept(this);
    }

    public void visit(AnyComparisonExpression anyComparisonExpression) {
        anyComparisonExpression.GetSubSelect().getSelectBody().accept(this);
    }

    public void visit(SubJoin subjoin) {
        subjoin.getLeft().accept(this);
        subjoin.getJoin().getRightItem().accept(this);
    }

    public void visit(Concat concat) {
        this.visitBinaryExpression(concat);
    }

    public void visit(Matches matches) {
        this.visitBinaryExpression(matches);
    }

    public void visit(BitwiseAnd bitwiseAnd) {
        this.visitBinaryExpression(bitwiseAnd);
    }

    public void visit(BitwiseOr bitwiseOr) {
        this.visitBinaryExpression(bitwiseOr);
    }

    public void visit(BitwiseXor bitwiseXor) {
        this.visitBinaryExpression(bitwiseXor);
    }
}

